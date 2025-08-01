package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.PacoteDTO;
import br.bom.flexlog.academic.dto.PacoteSaidaDTO;
import br.bom.flexlog.academic.entity.*;
import br.bom.flexlog.academic.exeptions.ConflictExeption;
import br.bom.flexlog.academic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PacoteService {

    @Autowired
    private PacoteRepository pacoteRepository;

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Autowired
    private StatusPacoteRepository statusPacoteRepository;

    @Autowired
    private StatusAgendamentoRepository statusAgendamentoRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    public List<PacoteDTO> ListarTodos(){
        List<Pacote> pacotes = pacoteRepository.findAll();
        return pacotes.stream().map(PacoteDTO::new).toList();
    }
    public PacoteDTO buscarPorId(Integer id){
        return new PacoteDTO(pacoteRepository.findById(id).get());
    }

    public PacoteDTO inserir(PacoteDTO dto) throws Exception {
        try {
            Transportadora transportadora = transportadoraRepository.findById(dto.getTransportadora().getIdTransportadora())
                    .orElseThrow(() -> new Exception("Transportadora não encontrada"));

            Pacote pacote = new Pacote(dto);
            pacote.setTransportadora(transportadora);

            // Busca o status inicial do banco, não crie novo objeto vazio!
            StatusPacote statusInicial = statusPacoteRepository.findById(1)
                    .orElseThrow(() -> new Exception("Status inicial não encontrado"));

            StatusAgendamento agendamentoIni = statusAgendamentoRepository.findById(1)
                    .orElseThrow(() -> new Exception("Agendamento inicial não encontrado"));

            // Cria a chave composta do históricoStatus e historicoAgendamento
            HistoricoStatusPK chave = new HistoricoStatusPK(pacote, statusInicial);
            HistoricoAgendamentoPK chave2 = new HistoricoAgendamentoPK(pacote , agendamentoIni);

            // Cria o histórico, já com a data atual
            HistoricoStatusPacote historicoStatus = new HistoricoStatusPacote();
            historicoStatus.setId(chave);
            historicoStatus.setDataStatus(new Date());
            HistoricoAgendamentoPacote historicoAgendamento = new HistoricoAgendamentoPacote();
            historicoAgendamento.setId(chave2);
            historicoAgendamento.setDtModificacao(new Date());


            // Inicializa a lista e adiciona os históricos de Status e Agendamento
            List<HistoricoStatusPacote> historicosStatus = new ArrayList<>();
            historicosStatus.add(historicoStatus);
            pacote.setHistoricosStatus(historicosStatus);
            pacote.setUltimoStatus(statusInicial);
            pacote.setUltimoAgendamento(agendamentoIni);

            List<HistoricoAgendamentoPacote> historicosAgendamento = new ArrayList<>();
            historicosAgendamento.add(historicoAgendamento);
            pacote.setHistoricosAgendamento(historicosAgendamento);
            String linkGerado = "https://localhost:5173/agendamento/" + pacote.getCodigoRastreio();
            pacote.setLink(linkGerado);


            // Salva o pacote junto com o status e agendamento (cascade ALL vai propagar)
            Pacote salvo = pacoteRepository.save(pacote);

            return new PacoteDTO(salvo);

        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar pacote: " + e.getMessage(), e);
        }
    }
    public PacoteDTO efetuarSaida(int idPacote, PacoteSaidaDTO dto) throws Exception {
        try {
            Pacote pacote = pacoteRepository.findById(idPacote)
                    .orElseThrow(() -> new Exception("Pacote não encontrado"));

            // Aqui verifica se já existe saída
            StatusPacote statusEmTransporte = statusPacoteRepository.findById(2)
                    .orElseThrow(() -> new Exception("Status não encontrado"));

            // Supondo que se o último status já for "Em Transporte", a saída já está feita
            if (pacote.getUltimoStatus() != null &&
                    pacote.getUltimoStatus().getIdStatusPacote() == statusEmTransporte.getIdStatusPacote()) {
                throw new ConflictExeption("Saída já foi registrada para este pacote.");
            }

            Entregador entregador = entregadorRepository.findById(dto.getEntregador().getIdUsuario())
                    .orElseThrow(() -> new Exception("Entregador não encontrado"));

            pacote.setEntregador(entregador);

            HistoricoStatusPacote historico = new HistoricoStatusPacote();
            historico.setId(new HistoricoStatusPK(pacote, statusEmTransporte));
            historico.setDataStatus(new Date());

            pacote.setUltimoStatus(statusEmTransporte);

            if (pacote.getHistoricosStatus() == null) {
                pacote.setHistoricosStatus(new ArrayList<>());
            }
            pacote.getHistoricosStatus().add(historico);

            Pacote atualizado = pacoteRepository.save(pacote);

            return new PacoteDTO(atualizado);

        } catch (ConflictExeption e) {
            throw e; // deixa passar para o controller tratar
        } catch (Exception e) {
            throw new Exception("Erro ao efetuar saída do pacote: " + e.getMessage(), e);
        }
    }









}

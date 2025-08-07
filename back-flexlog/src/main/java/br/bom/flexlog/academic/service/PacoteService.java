package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.PacoteDTO;
import br.bom.flexlog.academic.dto.PacoteRollbackDTO;
import br.bom.flexlog.academic.dto.PacoteSaidaDTO;
import br.bom.flexlog.academic.entity.*;
import br.bom.flexlog.academic.exeptions.ConflictExeption;
import br.bom.flexlog.academic.exeptions.NotFoundExeption;
import br.bom.flexlog.academic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public List<PacoteDTO> ListarTodos() {
        List<Pacote> pacotes = pacoteRepository.findAll();
        return pacotes.stream().map(PacoteDTO::new).toList();
    }

    public PacoteDTO buscarPorId(Integer id) throws NotFoundExeption {
        Pacote pacote = pacoteRepository.findById(id).orElseThrow(() -> new NotFoundExeption("Pacote não encontrado"));
        if (pacote == null) {
            throw new NotFoundExeption("Pacote não encontrado");
        }
        return new PacoteDTO(pacote);
    }



    public PacoteDTO buscarporCodigoRastreio(String codigoRastreio) throws NotFoundExeption {
        Pacote pacote = pacoteRepository.findCodigoRastreio(codigoRastreio);
        if (pacote == null) {
            throw new NotFoundExeption("Pacote não encontrado");
        }
        return new PacoteDTO(pacote);
    }


    public List<PacoteRollbackDTO> listarPacotesComFalhaEntregaAindaComEntregador() {
        List<Pacote> pacotes = pacoteRepository.findPacotesComFalhaEntregaAindaComEntregador();
        return pacotes.stream()
                .map(PacoteRollbackDTO::new)
                .toList();
    }


    public PacoteDTO inserir(PacoteDTO dto) throws Exception{
        Pacote pacoteJaExiste = pacoteRepository.findCodigoRastreio(dto.getCodigoRastreio());
        if(pacoteJaExiste == null){
            Transportadora transportadora = transportadoraRepository.findById(dto.getTransportadora().getIdTransportadora())
                        .orElseThrow(() -> new Exception("Transportadora não encontrada"));

            Pacote pacote = new Pacote(dto);
            pacote.setTransportadora(transportadora);

            StatusPacote statusInicial = statusPacoteRepository.findById(1)
                        .orElseThrow(() -> new Exception("Status inicial não encontrado"));

            StatusAgendamento agendamentoIni = statusAgendamentoRepository.findById(1)
                        .orElseThrow(() -> new Exception("Agendamento inicial não encontrado"));

            HistoricoStatusPK chave = new HistoricoStatusPK(pacote, statusInicial, new Date());
            HistoricoAgendamentoPK chave2 = new HistoricoAgendamentoPK(pacote, agendamentoIni);

            HistoricoStatusPacote historicoStatus = new HistoricoStatusPacote();
            historicoStatus.setId(chave);

            HistoricoAgendamentoPacote historicoAgendamento = new HistoricoAgendamentoPacote();
            historicoAgendamento.setId(chave2);
            historicoAgendamento.setDtModificacao(new Date());

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

            Pacote salvo = pacoteRepository.save(pacote);

            return new PacoteDTO(salvo);
        }else{

            throw new ConflictExeption("Pacote ja cadastrado" );
        }

    }

    public PacoteDTO efetuarSaida(int idPacote, PacoteSaidaDTO dto) throws Exception {
        try {
            Pacote pacote = pacoteRepository.findById(idPacote)
                    .orElseThrow(() -> new Exception("Pacote não encontrado"));

            StatusPacote statusEmTransporte = statusPacoteRepository.findById(2)
                    .orElseThrow(() -> new Exception("Status não encontrado"));

            Date agora = new Date();
            LocalDate hoje = agora.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            boolean jaSaiuHoje = pacote.getHistoricosStatus().stream()
                    .anyMatch(h -> h.getId().getStatus().getIdStatusPacote() == statusEmTransporte.getIdStatusPacote()
                            && h.getId().getDataStatus().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(hoje));

            if (jaSaiuHoje) {
                throw new ConflictExeption("Saída já registrada para este pacote na data de hoje.");
            }

            Entregador entregador = entregadorRepository.findById(dto.getEntregador().getIdUsuario())
                    .orElseThrow(() -> new Exception("Entregador não encontrado"));

            pacote.setEntregador(entregador);

            HistoricoStatusPacote historico = new HistoricoStatusPacote();
            historico.setId(new HistoricoStatusPK(pacote, statusEmTransporte, agora));

            pacote.setUltimoStatus(statusEmTransporte);

            if (pacote.getHistoricosStatus() == null) {
                pacote.setHistoricosStatus(new ArrayList<>());
            }

            pacote.getHistoricosStatus().add(historico);

            Pacote atualizado = pacoteRepository.save(pacote);

            return new PacoteDTO(atualizado);

        } catch (ConflictExeption e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Erro ao efetuar saída do pacote: " + e.getMessage(), e);
        }
    }

    public void rollbackEntrega(int idPacote) {
        try {
            Pacote pacote = pacoteRepository.findById(idPacote)
                    .orElseThrow(() -> new Exception("Pacote não encontrado"));

            int idEntregador = pacote.getEntregador().getIdUsuario();

            // Verifica se o pacote está com esse entregador
            if (pacote.getEntregador() == null || pacote.getEntregador().getIdUsuario() != idEntregador) {
                throw new Exception("Este pacote não está com o entregador informado.");
            }

            // Atualiza o status do pacote para algo como "Retornou ao centro logístico" (exemplo: idStatus = 4)
            StatusPacote statusFalha = statusPacoteRepository.findById(5)
                    .orElseThrow(() -> new Exception("Status de falha não encontrado"));
            pacote.setUltimoStatus(statusFalha);



            //FALTA ATUALIZAR TBM O STATUS DE AGENDAMENTO PARA AGUARDANDO AGENDAMENTO

            // Desassocia o entregador
            pacote.setEntregador(null);

            // CRIA A CHAVE COMPOSTA PRA GRAVAR O STATUS NA LISTA DE STATUSPACOTE
            HistoricoStatusPK chave = new HistoricoStatusPK(pacote, statusFalha, new Date());
            HistoricoStatusPacote historico = new HistoricoStatusPacote();
            historico.setId(chave);
            if (pacote.getHistoricosStatus() == null) {
                pacote.setHistoricosStatus(new ArrayList<>());
            }

            pacote.getHistoricosStatus().add(historico);

            // Persiste alterações
            pacoteRepository.save(pacote);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar rollback do pacote: " + e.getMessage());
        }
    }


}

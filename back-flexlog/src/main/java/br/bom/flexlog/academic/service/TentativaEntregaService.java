package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.TentativaEntregaDTO;
import br.bom.flexlog.academic.entity.*;
import br.bom.flexlog.academic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TentativaEntregaService {

    @Autowired
    private TentativaEntregaRepository tentativaEntregaRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private PacoteRepository pacoteRepository;

    @Autowired
    private StatusPacoteRepository statusPacoteRepository; // repositório para buscar StatusPacote

    @Autowired
    private HistoricoStatusPacoteRepository historicoStatusPacoteRepository; // para salvar o histórico

    public TentativaEntrega criarTentativaEntrega(TentativaEntregaDTO dto) {

        Entregador entregador = entregadorRepository.findById(dto.getIdEntregador())
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        Pacote pacote = pacoteRepository.findById(dto.getIdPacote())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        TentativaEntrega tentativa = new TentativaEntrega(
                dto.getRecebedor(),
                dto.getStatusEntrega(),
                dto.getFalhaEntrega(),
                dto.getAssinaturaRecebedor(),
                entregador,
                pacote
        );

        TentativaEntrega tentativaSalva = tentativaEntregaRepository.save(tentativa);

        // Busca os status relevantes
        StatusPacote statusEntregue = statusPacoteRepository.findById(3)
                .orElseThrow(() -> new RuntimeException("Status 'Entregue com sucesso' não encontrado"));

        StatusPacote statusFalha = statusPacoteRepository.findById(4)
                .orElseThrow(() -> new RuntimeException("Status 'Tentativa de entrega com falha' não encontrado"));

        // Verifica se o status enviado é 'Entregue com sucesso' ou 'Tentativa de entrega com falha'
        if (dto.getStatusEntrega().equalsIgnoreCase(statusEntregue.getNomeStatusPacote())
                || dto.getStatusEntrega().equals("3")
                || dto.getStatusEntrega().equalsIgnoreCase(statusFalha.getNomeStatusPacote())
                || dto.getStatusEntrega().equals("4")) {

            // Define qual status vai gravar (entregue ou falha)
            StatusPacote statusParaSalvar = null;
            if (dto.getStatusEntrega().equalsIgnoreCase(statusEntregue.getNomeStatusPacote()) || dto.getStatusEntrega().equals("3")) {
                statusParaSalvar = statusEntregue;
            } else if (dto.getStatusEntrega().equalsIgnoreCase(statusFalha.getNomeStatusPacote()) || dto.getStatusEntrega().equals("4")) {
                statusParaSalvar = statusFalha;
            }

            HistoricoStatusPK pk = new HistoricoStatusPK(pacote, statusParaSalvar);

            // Verifica se esse histórico já existe para evitar duplicidade
            boolean existe = historicoStatusPacoteRepository.existsById(pk);

            if (!existe) {
                HistoricoStatusPacote historico = new HistoricoStatusPacote();
                historico.setId(pk);
                historico.setPacote(pacote);
                historico.setStatus(statusParaSalvar);
                historico.setDataStatus(new Date());

                historicoStatusPacoteRepository.save(historico);

                // Atualiza último status do pacote
                pacote.setUltimoStatus(statusParaSalvar);

                pacoteRepository.save(pacote);
            }
        }

        return tentativaSalva;
    }


}
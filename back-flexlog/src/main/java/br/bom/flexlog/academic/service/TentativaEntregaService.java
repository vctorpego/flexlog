package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.TentativaEntregaDTO;
import br.bom.flexlog.academic.entity.*;
import br.bom.flexlog.academic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    public static Date zerarHora(Date data) {
    Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
}

    public TentativaEntrega criarTentativaEntrega(TentativaEntregaDTO dto) {
       // System.out.println(dto);

        Entregador entregador = entregadorRepository.findById(dto.getIdEntregador())
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        Pacote pacote = pacoteRepository.findById(dto.getIdPacote())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        // busca status relevantes
        StatusPacote statusEntregue = statusPacoteRepository.findById(3)
                .orElseThrow(() -> new RuntimeException("Status 'Entregue com sucesso' não encontrado"));

        StatusPacote statusFalha = statusPacoteRepository.findById(4)
                .orElseThrow(() -> new RuntimeException("Status 'Tentativa de entrega com falha' não encontrado"));

        StatusPacote statusParaSalvar = null;
        //verifica qual dos status é pra salvar no historicoStatus se deu falha ou se foi entregue
        if (dto.getStatusEntrega().equalsIgnoreCase(statusEntregue.getNomeStatusPacote()) || dto.getStatusEntrega().equals("3")) {
            statusParaSalvar = statusEntregue;
        } else if (dto.getStatusEntrega().equalsIgnoreCase(statusFalha.getNomeStatusPacote()) || dto.getStatusEntrega().equals("4")) {
            statusParaSalvar = statusFalha;
        }

        if (statusParaSalvar == null) {

            return null;
        }

        Date hojeSemHora = zerarHora(new Date());

        boolean existe = historicoStatusPacoteRepository.existsByPacoteAndStatusAndData(pacote, statusParaSalvar, hojeSemHora);

        if (existe) {
            // ja existe hoje nao salvar
            return null;
        }

        TentativaEntrega tentativa = new TentativaEntrega(
                dto.getRecebedor(),
                dto.getStatusEntrega(),
                dto.getFalhaEntrega(),
                dto.getAssinaturaRecebedor(),
                entregador,
                pacote
        );

        TentativaEntrega tentativaSalva = tentativaEntregaRepository.save(tentativa);

        HistoricoStatusPK pk = new HistoricoStatusPK(pacote, statusParaSalvar, new Date());

        HistoricoStatusPacote historico = new HistoricoStatusPacote();
        historico.setId(pk);
        historico.setPacote(pacote);
        historico.setStatus(statusParaSalvar);

        historicoStatusPacoteRepository.save(historico);

        pacote.setUltimoStatus(statusParaSalvar);
        pacoteRepository.save(pacote);

        return tentativaSalva;
    }





}
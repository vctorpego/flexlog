package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class HistoricoAgendamentoPacote implements Serializable {

    @EmbeddedId
    private HistoricoAgendamentoPK id;


    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dtModificacao;

    @Column
    private LocalDateTime agendamento;

    public HistoricoAgendamentoPacote(Pacote pacote, StatusAgendamento status, Date dtModificacao, LocalDateTime agendamento) {
        this.dtModificacao = dtModificacao;
        this.agendamento = agendamento;
    }
    public HistoricoAgendamentoPacote() {

    }

    public HistoricoAgendamentoPK getId() {
        return id;
    }

    public void setId(HistoricoAgendamentoPK id) {
        this.id = id;
    }

    public Date getDtModificacao() {
        return dtModificacao;
    }

    public void setDtModificacao(Date dtModificacao) {
        this.dtModificacao = dtModificacao;
    }

    public LocalDateTime getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(LocalDateTime agendamento) {
        this.agendamento = agendamento;
    }
}

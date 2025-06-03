package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class HistoricoAgendamentoPacote implements Serializable {

    @EmbeddedId
    private HistoricoAgendamentoPK id;

    @ManyToOne
    @MapsId("idPacote") // vincula ao campo da chave composta
    @JoinColumn(name = "id_pacote")
    private Pacote pacote;

    @ManyToOne
    @MapsId("idStatusAgendamento")
    @JoinColumn(name = "id_status_agendamento")
    private StatusAgendamento status;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dtModificacao;

    public HistoricoAgendamentoPacote(Pacote pacote, StatusAgendamento status, Date dtModificacao) {
        this.pacote = pacote;
        this.status = status;
        this.dtModificacao = dtModificacao;
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

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }
}

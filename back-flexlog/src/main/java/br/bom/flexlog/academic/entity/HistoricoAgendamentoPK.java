package br.bom.flexlog.academic.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class HistoricoAgendamentoPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idPacote")
    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "idStatusAgendamento")
    private StatusAgendamento status;

    public HistoricoAgendamentoPK(Pacote pacote, StatusAgendamento status) {
        this.pacote = pacote;
        this.status = status;
    }

    public HistoricoAgendamentoPK() {
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}

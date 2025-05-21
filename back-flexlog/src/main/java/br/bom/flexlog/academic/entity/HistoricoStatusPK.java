package br.bom.flexlog.academic.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class HistoricoStatusPK  implements Serializable {
    @ManyToOne
    @JoinColumn(name = "idPacote")
    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "idStatusPacote")
    private StatusPacote status;

    public HistoricoStatusPK(StatusPacote status, Pacote pacote) {
        this.status = status;
        this.pacote = pacote;
    }

    public HistoricoStatusPK() {
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public StatusPacote getStatus() {
        return status;
    }

    public void setStatus(StatusPacote status) {
        this.status = status;
    }
}

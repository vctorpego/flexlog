package br.bom.flexlog.academic.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HistoricoStatusPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_pacote")
    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "id_status_pacote")
    private StatusPacote status;

    public HistoricoStatusPK() {}

    public HistoricoStatusPK(Pacote pacote, StatusPacote status) {
        this.pacote = pacote;
        this.status = status;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public StatusPacote getStatusPacote() {
        return status;
    }

    public void setStatusPacote(StatusPacote status) {
        this.status = status;
    }

}

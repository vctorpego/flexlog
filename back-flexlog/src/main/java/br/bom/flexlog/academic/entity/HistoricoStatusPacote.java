package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
public class HistoricoStatusPacote  implements Serializable {

    @EmbeddedId
    private HistoricoStatusPK id;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dataStatus;

    public HistoricoStatusPacote() {}

    public HistoricoStatusPacote(Pacote pacote, StatusPacote status, Date dataStatus) {
        this.id = new HistoricoStatusPK(pacote, status);
        this.dataStatus = dataStatus;
    }

    public Pacote getPacote() {
        return id.getPacote();
    }

    public void setPacote(Pacote pacote) {
        this.id.setPacote(pacote);
    }

    public StatusPacote getStatus() {
        return id.getStatusPacote();
    }

    public void setStatus(StatusPacote status) {
        this.id.setStatusPacote(status);
    }

    public Date getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Date dataStatus) {
        this.dataStatus = dataStatus;
    }
}

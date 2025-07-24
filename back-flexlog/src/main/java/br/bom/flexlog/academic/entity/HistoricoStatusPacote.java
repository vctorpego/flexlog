package br.bom.flexlog.academic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.io.Serializable;

import java.util.Date;


@Entity
public class HistoricoStatusPacote implements Serializable {

    @EmbeddedId
    private HistoricoStatusPK id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date dataStatus;

    public HistoricoStatusPacote() {}

    public HistoricoStatusPacote(Pacote pacote, StatusPacote status, Date dataStatus) {
        this.id = new HistoricoStatusPK(pacote, status);
        this.dataStatus = dataStatus;
    }
    @JsonIgnore
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

    public void setDataStatus(Date dataHora) {
        this.dataStatus = dataHora;
    }

    public void setId(HistoricoStatusPK id) {
        this.id = id;
    }

}

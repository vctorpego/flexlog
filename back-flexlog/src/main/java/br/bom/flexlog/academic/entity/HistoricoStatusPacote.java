package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
public class HistoricoStatusPacote  implements Serializable {

    @EmbeddedId
    private HistoricoStatusPK id;

    @ManyToOne
    @MapsId("idPacote") // vincula ao campo da chave composta
    @JoinColumn(name = "id_pacote")
    private Pacote pacote;

    @ManyToOne
    @MapsId("idStatusPacote")
    @JoinColumn(name = "id_status_pacote")
    private StatusPacote status;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dataStatus;

    public HistoricoStatusPacote() {}

    public HistoricoStatusPacote(Pacote pacote, StatusPacote status, Date dataStatus) {
        this.id = new HistoricoStatusPK(pacote.getIdPacote(), status.getIdStatusPacote());
        this.pacote = pacote;
        this.status = status;
        this.dataStatus = dataStatus;
    }

    public HistoricoStatusPK getId() {
        return id;
    }

    public void setId(HistoricoStatusPK id) {
        this.id = id;
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

    public Date getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Date dataStatus) {
        this.dataStatus = dataStatus;
    }
}

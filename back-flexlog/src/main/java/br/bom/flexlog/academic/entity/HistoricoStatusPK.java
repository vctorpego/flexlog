package br.bom.flexlog.academic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Embeddable
public class HistoricoStatusPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_pacote")

    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "id_status_pacote")
    @JsonBackReference
    private StatusPacote status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date dataStatus;



    public HistoricoStatusPK() {}

    public HistoricoStatusPK(Pacote pacote, StatusPacote status, Date dataStatus) {
        this.pacote = pacote;
        this.status = status;
        this.dataStatus = dataStatus;

    }
    @JsonIgnore
    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricoStatusPK that = (HistoricoStatusPK) o;
        return Objects.equals(pacote, that.pacote) && Objects.equals(status, that.status) && Objects.equals(dataStatus, that.dataStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pacote, status, dataStatus);
    }

    public StatusPacote getStatusPacote() {
        return status;
    }

    public void setStatusPacote(StatusPacote status) {
        this.status = status;
    }

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    public Date getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Date dataStatus) {
        this.dataStatus = dataStatus;
    }

    public StatusPacote getStatus() {
        return status;
    }

    public void setStatus(StatusPacote status) {
        this.status = status;
    }
}

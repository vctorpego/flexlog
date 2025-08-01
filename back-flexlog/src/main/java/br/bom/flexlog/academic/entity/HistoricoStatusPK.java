package br.bom.flexlog.academic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import java.io.Serializable;
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



    public HistoricoStatusPK() {}

    public HistoricoStatusPK(Pacote pacote, StatusPacote status) {
        this.pacote = pacote;
        this.status = status;
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
        return Objects.equals(pacote, that.pacote) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pacote, status);
    }

    public StatusPacote getStatusPacote() {
        return status;
    }

    public void setStatusPacote(StatusPacote status) {
        this.status = status;
    }

}

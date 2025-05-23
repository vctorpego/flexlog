package br.bom.flexlog.academic.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HistoricoStatusPK implements Serializable {

    private int idPacote;
    private int idStatusPacote;

    public HistoricoStatusPK() {}

    public HistoricoStatusPK(int idPacote, int idStatusPacote) {
        this.idPacote = idPacote;
        this.idStatusPacote = idStatusPacote;
    }

    public int getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(int idPacote) {
        this.idPacote = idPacote;
    }

    public int getIdStatusPacote() {
        return idStatusPacote;
    }

    public void setIdStatusPacote(int idStatusPacote) {
        this.idStatusPacote = idStatusPacote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricoStatusPK)) return false;
        HistoricoStatusPK that = (HistoricoStatusPK) o;
        return idPacote == that.idPacote &&
                idStatusPacote == that.idStatusPacote;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPacote, idStatusPacote);
    }
}

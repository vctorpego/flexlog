package br.bom.flexlog.academic.entity;


import jakarta.persistence.Embeddable;


import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HistoricoAgendamentoPK implements Serializable {


    private int idPacote;
    private int idStatusAgendamento;


    public HistoricoAgendamentoPK(int idStatusAgendamento, int idPacote) {
        this.idStatusAgendamento = idStatusAgendamento;
        this.idPacote = idPacote;
    }

    public HistoricoAgendamentoPK() {

    }

    public int getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(int idPacote) {
        this.idPacote = idPacote;
    }

    public int getIdStatusAgendamento() {
        return idStatusAgendamento;
    }

    public void setIdStatusAgendamento(int idStatusAgendamento) {
        this.idStatusAgendamento = idStatusAgendamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricoAgendamentoPK that = (HistoricoAgendamentoPK) o;
        return idPacote == that.idPacote && idStatusAgendamento == that.idStatusAgendamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPacote, idStatusAgendamento);
    }
}

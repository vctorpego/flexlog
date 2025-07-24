package br.bom.flexlog.academic.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class HistoricoAgendamentoPK implements Serializable {


    @ManyToOne
    @JoinColumn(name = "id_pacote")
    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "id_status_agendamento")
    private StatusAgendamento statusAgendamento;

    public HistoricoAgendamentoPK(Pacote pacote, StatusAgendamento statusAgendamento) {
        this.pacote = pacote;
        this.statusAgendamento = statusAgendamento;
    }

    public HistoricoAgendamentoPK() {
    }
    @JsonIgnore
    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public StatusAgendamento getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}

package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class StatusAgendamento implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idStatusAgendamento;
    @Column
    private String nomeAgendamento;
    @Column
    private String horarioPrevisto;

    public StatusAgendamento(int idStatusAgendamento, String nomeAgendamento, String horarioPrevisto) {
        this.idStatusAgendamento = idStatusAgendamento;
        this.nomeAgendamento = nomeAgendamento;
        this.horarioPrevisto = horarioPrevisto;
    }

    public StatusAgendamento() {

    }

    public StatusAgendamento(String nomeAgendamento) {
        this.nomeAgendamento = nomeAgendamento;
    }

    public int getIdStatusAgendamento() {
        return idStatusAgendamento;
    }

    public void setIdStatusAgendamento(int idStatusAgendamento) {
        this.idStatusAgendamento = idStatusAgendamento;
    }

    public String getNomeAgendamento() {
        return nomeAgendamento;
    }

    public void setNomeAgendamento(String nomeAgendamento) {
        this.nomeAgendamento = nomeAgendamento;
    }

    public String getHorarioPrevisto() {
        return horarioPrevisto;
    }

    public void setHorarioPrevisto(String horarioPrevisto) {
        this.horarioPrevisto = horarioPrevisto;
    }
}

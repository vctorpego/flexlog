package br.bom.flexlog.academic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StatusAgendamento implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idStatusAgendamento;
    @Column
    private String nomeAgendamento;
    @Column
    private String horarioPrevisto;

    @OneToMany(mappedBy = "ultimoAgendamento")
    @JsonIgnore
    private List<Pacote> pacotes = new ArrayList<>();

    public List<Pacote> getPacotes() {
        return pacotes;
    }

    public void setPacotes(List<Pacote> pacotes) {
        this.pacotes = pacotes;
    }

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

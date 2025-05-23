package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StatusPacote implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idStatusPacote;
    @Column
    private String nomeStatusPacote;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<HistoricoStatusPacote> historicos = new ArrayList<>();


    public StatusPacote(String nomeStatusPacote) {
        this.nomeStatusPacote = nomeStatusPacote;
    }

    public StatusPacote() {
    }

    public int getIdStatusPacote() {
        return idStatusPacote;
    }

    public void setIdStatusPacote(int idStatusPacote) {
        this.idStatusPacote = idStatusPacote;
    }

    public String getNomeStatusPacote() {
        return nomeStatusPacote;
    }

    public void setNomeStatusPacote(String nomeStatusPacote) {
        this.nomeStatusPacote = nomeStatusPacote;
    }

    public List<HistoricoStatusPacote> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<HistoricoStatusPacote> historicos) {
        this.historicos = historicos;
    }
}

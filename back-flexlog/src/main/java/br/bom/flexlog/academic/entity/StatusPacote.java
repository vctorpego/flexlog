package br.bom.flexlog.academic.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Id;


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
    @OneToMany(mappedBy = "ultimoStatus")
    @JsonIgnore
    private List<Pacote> pacotes = new ArrayList<>();



    public StatusPacote(String nomeStatusPacote) {
        this.nomeStatusPacote = nomeStatusPacote;
    }

    public StatusPacote() {
    }

    public List<Pacote> getPacotes() {
        return pacotes;
    }

    public void setPacotes(List<Pacote> pacotes) {
        this.pacotes = pacotes;
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


}

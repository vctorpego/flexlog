package br.bom.flexlog.academic.entity;



import jakarta.persistence.*;
import java.io.Serializable;


@Entity
public class StatusPacote implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idStatusPacote;
    @Column
    private String nomeStatusPacote;



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


}

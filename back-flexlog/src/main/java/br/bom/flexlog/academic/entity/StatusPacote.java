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
}

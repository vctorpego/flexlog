package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;

public class StatusAgendamento implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idStatusAgendamento;
    @Column
    private String nomeAgendamento;
    @Column
    private String horarioPrevisto;
}

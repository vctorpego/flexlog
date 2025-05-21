package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


public class Pacote  implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idPacote;
    @Column
    private String codigoRastreio;
    @Column
    private String destinatario;
    @Column
    private String cep;
    @Column
    private String cidade;
    @Column
    private String bairro;
    @Column
    private String rua;
    @Column
    private String numeroEndereco;
    @Temporal(value = TemporalType.DATE)
    @Column(updatable = false, nullable = false)
    private Date dtEntrada;
    @Temporal(value = TemporalType.DATE)
    @Column()
    private Date dtSaida;




}

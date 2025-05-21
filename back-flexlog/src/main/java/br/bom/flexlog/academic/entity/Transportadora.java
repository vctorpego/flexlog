package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table( name = "transportadora")
public class Transportadora implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idTransportadora;
    @Column
    private String nomeSocial;
    @Column
    private String cnpj;

    public Transportadora(String nomeSocial, String cnpj) {
        this.nomeSocial = nomeSocial;
        this.cnpj = cnpj;
    }

    public Transportadora() {
    }

    public int getIdTransportadora() {
        return idTransportadora;
    }

    public void setIdTransportadora(int idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }
}

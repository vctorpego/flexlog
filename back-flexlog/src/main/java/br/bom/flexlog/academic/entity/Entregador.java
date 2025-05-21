package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

@Entity
public class Entregador  extends Usuario{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idEntregador;
    @Column
    private String cnh;

    public Entregador(String cnh) {
        this.cnh = cnh;
    }
    public Entregador() {

    }


    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public int getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(int idEntregador) {
        this.idEntregador = idEntregador;
    }
}

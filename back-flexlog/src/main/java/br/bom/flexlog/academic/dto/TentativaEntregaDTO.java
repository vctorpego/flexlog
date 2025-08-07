package br.bom.flexlog.academic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

public class TentativaEntregaDTO {
    private String recebedor;
    private String statusEntrega;
    private String falhaEntrega; // pode ser null se for entregue
    private byte[] assinaturaRecebedor;
    private int idEntregador;
    private int idPacote;

    // Getters e Setters

    public String getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(String recebedor) {
        this.recebedor = recebedor;
    }

    public int getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(int idPacote) {
        this.idPacote = idPacote;
    }

    public int getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(int idEntregador) {
        this.idEntregador = idEntregador;
    }

    @Override
    public String toString() {
        return "TentativaEntregaDTO{" +
                "recebedor='" + recebedor + '\'' +
                ", statusEntrega='" + statusEntrega + '\'' +
                ", falhaEntrega='" + falhaEntrega + '\'' +
                ", assinaturaRecebedor=" + Arrays.toString(assinaturaRecebedor) +
                ", idEntregador=" + idEntregador +
                ", idPacote=" + idPacote +
                '}';
    }

    public byte[] getAssinaturaRecebedor() {
        return assinaturaRecebedor;
    }

    public void setAssinaturaRecebedor(byte[] assinaturaRecebedor) {
        this.assinaturaRecebedor = assinaturaRecebedor;
    }

    public String getFalhaEntrega() {
        return falhaEntrega;
    }

    public void setFalhaEntrega(String falhaEntrega) {
        this.falhaEntrega = falhaEntrega;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }
}

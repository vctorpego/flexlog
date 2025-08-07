package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Transportadora;

public class TransportadoraLiteDTO {

    private int idTransportadora;
    private String nomeSocial;

    public TransportadoraLiteDTO() {
    }

    public TransportadoraLiteDTO(int idTransportadora, String nomeSocial) {
        this.idTransportadora = idTransportadora;
        this.nomeSocial = nomeSocial;
    }

    public TransportadoraLiteDTO(Transportadora transportadora) {
        this.idTransportadora = transportadora.getIdTransportadora();
        this.nomeSocial = transportadora.getNomeSocial();
    }

    public int getIdTransportadora() {
        return idTransportadora;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public void setIdTransportadora(int idTransportadora) {
        this.idTransportadora = idTransportadora;
    }
}

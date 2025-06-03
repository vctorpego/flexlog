package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Entregador;
import br.bom.flexlog.academic.entity.Transportadora;

public class TransportadoraDTO {
    private int idTransportadora;
    private String nomeSocial;
    private String cnpj;

    private String logradouro;
    private String numero;

    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public TransportadoraDTO() {
    }

    public TransportadoraDTO(int idTransportadora, String nomeSocial, String cnpj,
                             String logradouro, String numero, String complemento,
                             String bairro, String cidade, String estado, String cep) {
        this.idTransportadora = idTransportadora;
        this.nomeSocial = nomeSocial;
        this.cnpj = cnpj;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public TransportadoraDTO(Transportadora transportadora) {
        this.idTransportadora = transportadora.getIdTransportadora();
        this.nomeSocial = transportadora.getNomeSocial();
        this.cnpj = transportadora.getCnpj();

        this.logradouro = transportadora.getLogradouro();
        this.numero = transportadora.getNumero();
        this.complemento = transportadora.getComplemento();
        this.bairro = transportadora.getBairro();
        this.cidade = transportadora.getCidade();
        this.estado = transportadora.getEstado();
        this.cep = transportadora.getCep();
    }



    public int getIdTransportadora() {
        return idTransportadora;
    }

    public void setIdTransportadora(int idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

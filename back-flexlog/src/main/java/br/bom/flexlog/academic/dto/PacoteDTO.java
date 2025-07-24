package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.HistoricoStatusPacote;
import br.bom.flexlog.academic.entity.Pacote;

import java.util.List;

public class PacoteDTO {

    private int idPacote;
    private String codigoRastreio;
    private String destinatario;
    private String cep;
    private String cidade;
    private String bairro;
    private String rua;
    private String numeroEndereco;
    private TransportadoraLiteDTO transportadora;
    private String link;

    private List<HistoricoStatusPacote> historicoStatusPacoteList;



    public PacoteDTO() {
    }

    public List<HistoricoStatusPacote> getHistoricoStatusPacoteList() {
        return historicoStatusPacoteList;
    }

    public void setHistoricoStatusPacoteList(List<HistoricoStatusPacote> historicoStatusPacoteList) {
        this.historicoStatusPacoteList = historicoStatusPacoteList;
    }

    public PacoteDTO(Pacote pacote) {
        this.idPacote = pacote.getIdPacote();
        this.codigoRastreio = pacote.getCodigoRastreio();
        this.destinatario = pacote.getDestinatario();
        this.cep = pacote.getCep();
        this.cidade = pacote.getCidade();
        this.bairro = pacote.getBairro();
        this.rua = pacote.getRua();
        this.numeroEndereco = pacote.getNumeroEndereco();
        this.link = pacote.getLink();
        this.historicoStatusPacoteList = pacote.getHistoricosStatus();


        if (pacote.getTransportadora() != null) {
            this.transportadora = new TransportadoraLiteDTO(pacote.getTransportadora().getIdTransportadora());
        }
    }

    // Getters e Setters

    public int getIdPacote() {
        return idPacote;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setIdPacote(int idPacote) {
        this.idPacote = idPacote;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public TransportadoraLiteDTO getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(TransportadoraLiteDTO transportadora) {
        this.transportadora = transportadora;
    }
}

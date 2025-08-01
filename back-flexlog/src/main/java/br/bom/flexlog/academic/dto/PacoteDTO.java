package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.*;

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
    private StatusPacote ultimoStatus;
    private StatusAgendamento ultimoAgendamento;
    private List<HistoricoStatusPacote> historicoStatusPacoteList;
    private List <HistoricoAgendamentoPacote> historicoAgendamentoPacotes;



    public PacoteDTO() {
    }

    public List<HistoricoStatusPacote> getHistoricoStatusPacoteList() {
        return historicoStatusPacoteList;
    }

    public StatusAgendamento getUltimoAgendamento() {
        return ultimoAgendamento;
    }

    public List<HistoricoAgendamentoPacote> getHistoricoAgendamentoPacotes() {
        return historicoAgendamentoPacotes;
    }

    public void setHistoricoAgendamentoPacotes(List<HistoricoAgendamentoPacote> historicoAgendamentoPacotes) {
        this.historicoAgendamentoPacotes = historicoAgendamentoPacotes;
    }

    public void setUltimoAgendamento(StatusAgendamento ultimoAgendamento) {
        this.ultimoAgendamento = ultimoAgendamento;
    }

    public void setHistoricoStatusPacoteList(List<HistoricoStatusPacote> historicoStatusPacoteList) {
        this.historicoStatusPacoteList = historicoStatusPacoteList;
    }

    public StatusPacote getUltimoStatus() {
        return ultimoStatus;
    }

    public void setUltimoStatus(StatusPacote ultimoStatus) {
        this.ultimoStatus = ultimoStatus;
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
        this.ultimoStatus = pacote.getUltimoStatus();
        this.ultimoAgendamento = pacote.getUltimoAgendamento();
        this.historicoAgendamentoPacotes = pacote.getHistoricosAgendamento();



        if (pacote.getTransportadora() != null) {
            this.transportadora = new TransportadoraLiteDTO(pacote.getTransportadora().getIdTransportadora(),pacote.getTransportadora().getNomeSocial());
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

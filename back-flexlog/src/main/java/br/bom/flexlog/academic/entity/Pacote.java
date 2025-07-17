package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pacote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPacote;

    @Column(nullable = false)
    private String codigoRastreio;

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numeroEndereco;

    @ManyToOne
    @JoinColumn(name = "id_transportadora", nullable = false)
    private Transportadora transportadora;

    @OneToMany(mappedBy = "pacote", orphanRemoval = true)
    private List<TentativaEntrega> tentativas = new ArrayList<>();

    @OneToMany(mappedBy = "id.pacote", orphanRemoval = true)
    private List<HistoricoStatusPacote> historicosStatus = new ArrayList<>();

    @OneToMany(mappedBy = "pacote", orphanRemoval = true)
    private List<HistoricoAgendamentoPacote> historicosAgendamento = new ArrayList<>();


    public Pacote() {}

    public Pacote(String codigoRastreio, String destinatario, String cep, String cidade,
                  String bairro, String rua, String numeroEndereco, Transportadora transportadora) {
        this.codigoRastreio = codigoRastreio;
        this.destinatario = destinatario;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroEndereco = numeroEndereco;
        this.transportadora = transportadora;
    }

    public List<HistoricoStatusPacote> getHistoricosStatus() {
        return historicosStatus;
    }

    public void setHistoricosStatus(List<HistoricoStatusPacote> historicosStatus) {
        this.historicosStatus = historicosStatus;
    }


    public int getIdPacote() {
        return idPacote;
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

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public List<TentativaEntrega> getTentativas() {
        return tentativas;
    }

    public void setTentativas(List<TentativaEntrega> tentativas) {
        this.tentativas = tentativas;
    }

    public List<HistoricoAgendamentoPacote> getHistoricosAgendamento() {
        return historicosAgendamento;
    }

    public void setHistoricosAgendamento(List<HistoricoAgendamentoPacote> historicosAgendamento) {
        this.historicosAgendamento = historicosAgendamento;
    }
}

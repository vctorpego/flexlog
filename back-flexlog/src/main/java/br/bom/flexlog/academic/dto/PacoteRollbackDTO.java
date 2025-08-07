package br.bom.flexlog.academic.dto;


import br.bom.flexlog.academic.entity.*;


public class PacoteRollbackDTO {

    private int idPacote;
    private String codigoRastreio;
    private String destinatario;
    private TransportadoraLiteDTO transportadora;
    private StatusPacote ultimoStatus;
    private EntregadorPacoteDTO entregador;



    public PacoteRollbackDTO() {
    }



    public StatusPacote getUltimoStatus() {
        return ultimoStatus;
    }

    public void setUltimoStatus(StatusPacote ultimoStatus) {
        this.ultimoStatus = ultimoStatus;
    }

    public PacoteRollbackDTO(Pacote pacote) {
        this.idPacote = pacote.getIdPacote();
        this.codigoRastreio = pacote.getCodigoRastreio();
        this.destinatario = pacote.getDestinatario();
        this.ultimoStatus = pacote.getUltimoStatus();
        this.entregador = pacote.getEntregador() != null
                ? new EntregadorPacoteDTO(pacote.getEntregador())
                : null; //  proteção contra null



        if (pacote.getTransportadora() != null) {
            this.transportadora = new TransportadoraLiteDTO(pacote.getTransportadora().getIdTransportadora(),pacote.getTransportadora().getNomeSocial());
        }
    }

    // Getters e Setters

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



    public TransportadoraLiteDTO getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(TransportadoraLiteDTO transportadora) {
        this.transportadora = transportadora;
    }

    public EntregadorPacoteDTO getEntregador() {
        return entregador;
    }

    public void setEntregador(EntregadorPacoteDTO entregador) {
        this.entregador = entregador;
    }
}


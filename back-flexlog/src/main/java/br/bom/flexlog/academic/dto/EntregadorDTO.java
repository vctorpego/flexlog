package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Entregador;

import java.util.List;

public class EntregadorDTO {

    private int idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String telefoneUsuario;
    private String login;
    private String senhaUsuario;
    private String cnh;
    private Boolean isAdm;



    // Caso queira retornar tentativas associadas (opcional)

    public EntregadorDTO() {
    }

    public Boolean getIsAdm() {
        return isAdm;
    }

    public void setIsAdm(Boolean isAdm) {
        this.isAdm = isAdm;
    }

    public void setAdm(Boolean adm) {
        isAdm = adm;
    }



    public EntregadorDTO(Entregador entregador) {
        this.idUsuario = entregador.getIdUsuario();
        this.nomeUsuario = entregador.getNomeUsuario();
        this.emailUsuario = entregador.getEmailUsuario();
        this.login = entregador.getLogin();
        this.senhaUsuario = entregador.getSenhaUsuario();
        this.telefoneUsuario = entregador.getTelefoneUsuario();
        this.isAdm = entregador.getIsAdm();


        this.cnh = entregador.getCnh();


    }


    // Getters e Setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }


    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }


}

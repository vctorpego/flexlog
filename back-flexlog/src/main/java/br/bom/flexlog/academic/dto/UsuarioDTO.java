package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Usuario;
import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UsuarioDTO {

    private int idUsuario;
    private String emailUsuario;
    private String telefoneUsuario;
    private String nomeUsuario;
    private String login;
    private String senhaUsuario;
    @NotNull(message = "O campo isSuperAdm é obrigatório")
    private Boolean isSuperAdm;
    @NotNull(message = "O campo isAdm é obrigatório")
    private Boolean isAdm;
    private List<UsuarioPermissaoTela> usuarioPermissaoTelaListUsuario;

    private String tipoUsuarioLabel;

    public UsuarioDTO(Usuario usuario){
        BeanUtils.copyProperties(usuario, this);
    }

    public UsuarioDTO(){

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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

    public List<UsuarioPermissaoTela> getUsuarioPermissaoTelaListUsuario() {
        return usuarioPermissaoTelaListUsuario;
    }

    public void setUsuarioPermissaoTelaListUsuario(List<UsuarioPermissaoTela> usuarioPermissaoTelaListUsuario) {
        this.usuarioPermissaoTelaListUsuario = usuarioPermissaoTelaListUsuario;
    }

    public Boolean getIsAdm() {
        return isAdm;
    }

    public void setIsAdm(Boolean isAdm) {
        this.isAdm = isAdm;
    }

    public String getTipoUsuarioLabel() {
        return tipoUsuarioLabel;
    }

    public void setTipoUsuarioLabel(String tipoUsuarioLabel) {
        this.tipoUsuarioLabel = tipoUsuarioLabel;
    }

    public Boolean getIsSuperAdm() {
        return isSuperAdm;
    }

    public void setIsSuperAdm(Boolean isSuperAdm) {
        this.isSuperAdm = isSuperAdm;
    }

}

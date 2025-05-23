package br.bom.flexlog.academic.entity;

import br.bom.flexlog.academic.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.INTEGER)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(nullable = false)
    private String emailUsuario;

    @Column(nullable = false)
    private String telefoneUsuario;

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senhaUsuario;

    @Column(name = "is_super_adm", nullable = false)
    private Boolean isSuperAdm = false;

    @Column(name = "is_adm", nullable = false)
    private Boolean isAdm = false;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // evita referência cíclica na serialização JSON
    private List<UsuarioPermissaoTela> usuarioPermissaoTelaListUsuario;

    // Construtores
    public Usuario() {}

    public Usuario(UsuarioDTO usuarioDTO){
        BeanUtils.copyProperties(usuarioDTO, this);
    }

    public Usuario(int idUsuario, List<UsuarioPermissaoTela> usuarioPermissaoTelaListUsuario,
                   String senhaUsuario, String login, String nomeUsuario,
                   String emailUsuario, String telefoneUsuario) {
        this.idUsuario = idUsuario;
        this.usuarioPermissaoTelaListUsuario = usuarioPermissaoTelaListUsuario;
        this.senhaUsuario = senhaUsuario;
        this.login = login;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.telefoneUsuario = telefoneUsuario;
    }

    // Getters e setters
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

    public Boolean getIsSuperAdm() {
        return isSuperAdm;
    }

    public void setIsSuperAdm(Boolean isSuperAdm) {
        this.isSuperAdm = isSuperAdm;
    }

    public Boolean getIsAdm() {
        return isAdm;
    }

    public void setIsAdm(Boolean isAdm) {
        this.isAdm = isAdm;
    }

    public List<UsuarioPermissaoTela> getUsuarioPermissaoTelaListUsuario() {
        return usuarioPermissaoTelaListUsuario;
    }

    public void setUsuarioPermissaoTelaListUsuario(List<UsuarioPermissaoTela> usuarioPermissaoTelaListUsuario) {
        this.usuarioPermissaoTelaListUsuario = usuarioPermissaoTelaListUsuario;
    }

    // Métodos vazios podem ser removidos ou implementados futuramente
    public void login() {
        // lógica de login
    }

    public void logout() {
        // lógica de logout
    }
}

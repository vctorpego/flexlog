package br.bom.flexlog.academic.entity;

import br.bom.flexlog.academic.dto.UsuarioPermissaoTelaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "usuario_permissao_tela")
public class UsuarioPermissaoTela {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int idPermissaoTela;
    @ManyToOne
    private Tela tela;

    @ManyToOne
    private Permissao permissao;

    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    public UsuarioPermissaoTela(UsuarioPermissaoTelaDTO usuarioPermissaoTela){
        BeanUtils.copyProperties(usuarioPermissaoTela, this);
    }

    public UsuarioPermissaoTela() {
    }

    public int getIdPermissaoTela() {
        return idPermissaoTela;
    }

    public void setIdPermissaoTela(int idPermissaoTela) {
        this.idPermissaoTela = idPermissaoTela;
    }

    public Tela getTela() {
        return tela;
    }

    public void setTela(Tela tela) {
        this.tela = tela;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

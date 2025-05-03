package br.bom.flexlog.academic.entity;

import br.bom.flexlog.academic.dto.PermissaoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "permissao")
public class Permissao implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idPermisao;

    @Column(nullable = false)
    private String nomePermissao;

    @Column
    private String acaoPermissao;

    @OneToMany (mappedBy = "permissao")
    @JsonBackReference // Evita a serialização repetitiva
    private List<UsuarioPermissaoTela> usuarioPermissaoTelaListPermissao;

    public Permissao(PermissaoDTO permissao){
        BeanUtils.copyProperties(permissao, this);
    }

    public Permissao(){

    }


    public Permissao(String nomePermissao, String acaoPermissao) {
        this.nomePermissao = nomePermissao;
        this.acaoPermissao = acaoPermissao;
    }

    public int getIdPermissao() {
        return idPermisao;
    }

    public void setIdPermisao(int idPermisao) {
        this.idPermisao = idPermisao;
    }

    public String getNomePermissao() {
        return nomePermissao;
    }

    public void setNomePermissao(String nomePermissao) {
        this.nomePermissao = nomePermissao;
    }

    public String getAcaoPermissao() {
        return acaoPermissao;
    }

    public void setAcaoPermissao(String acaoPermissao) {
        this.acaoPermissao = acaoPermissao;
    }

    public List<UsuarioPermissaoTela> getUsuarioPermissaoTelaListPermissao() {
        return usuarioPermissaoTelaListPermissao;
    }

    public void setUsuarioPermissaoTelaListPermissao(List<UsuarioPermissaoTela> usuarioPermissaoTelaListPermissao) {
        this.usuarioPermissaoTelaListPermissao = usuarioPermissaoTelaListPermissao;
    }
}

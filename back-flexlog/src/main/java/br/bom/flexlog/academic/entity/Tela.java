package br.bom.flexlog.academic.entity;

import br.bom.flexlog.academic.dto.TelaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tela")
public class Tela implements Serializable{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int idTela;

    @Column(nullable = false)
    private String nomeTela;

    @Column (nullable = false)
    private String urlTela;

    @OneToMany(mappedBy = "tela")
    @JsonBackReference // Evita a serialização repetitiva
    private List<UsuarioPermissaoTela> usuarioPermissaoTelaListTela;

    public Tela(TelaDTO tela){
        BeanUtils.copyProperties(tela, this);
    }

    public Tela(){

    }

    public Tela(Object o, String vendas, String s) {
    }

    public Tela(String nomeTela, String urlTela) {
        this.nomeTela = nomeTela;
        this.urlTela = urlTela;
    }

    public int getIdTela() {
        return idTela;
    }

    public void setIdTela(int idTela) {
        this.idTela = idTela;
    }

    public String getNomeTela() {
        return nomeTela;
    }

    public void setNomeTela(String nomeTela) {
        this.nomeTela = nomeTela;
    }

    public String getUrlTela() {
        return urlTela;
    }

    public void setUrlTela(String urlTela) {
        this.urlTela = urlTela;
    }

    public List<UsuarioPermissaoTela> getUsuarioPermissaoTelaListTela() {
        return usuarioPermissaoTelaListTela;
    }

    public void setUsuarioPermissaoTelaListTela(List<UsuarioPermissaoTela> usuarioPermissaoTelaListTela) {
        this.usuarioPermissaoTelaListTela = usuarioPermissaoTelaListTela;
    }
}

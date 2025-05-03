package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Tela;
import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;
import org.springframework.beans.BeanUtils;
import java.util.List;

public class TelaDTO {
    private int idTela;
    private String nomeTela;
    private String urlTela;
    private List<UsuarioPermissaoTela> usuarioPermissaoTelaListTela;

    public TelaDTO(Tela tela){
        BeanUtils.copyProperties(tela, this);
    }

    public TelaDTO(){

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

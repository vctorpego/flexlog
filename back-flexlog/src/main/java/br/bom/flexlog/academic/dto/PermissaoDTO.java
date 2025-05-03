package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Permissao;
import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class PermissaoDTO {
    private int idPermisao;
    private String nomePermissao;
    private String acaoPermissao;
    private List<UsuarioPermissaoTela> usuarioPermissaoTelaListPermissao;

    public PermissaoDTO(Permissao permissao){
        BeanUtils.copyProperties(permissao, this);
    }

    public PermissaoDTO(){

    }

    public int getIdPermisao() {
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

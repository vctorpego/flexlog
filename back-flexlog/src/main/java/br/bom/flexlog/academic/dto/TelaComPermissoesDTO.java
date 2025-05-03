package br.bom.flexlog.academic.dto;

import java.util.Set;

public class TelaComPermissoesDTO {
    private String tela;
    private String urlTela;
    private Set<String> permissoes;

    public TelaComPermissoesDTO(String tela, String urlTela, Set<String> permissoes) {
        this.tela = tela;
        this.urlTela = urlTela;
        this.permissoes = permissoes;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    public String getUrlTela() {
        return urlTela;
    }

    public void setUrlTela(String urlTela) {
        this.urlTela = urlTela;
    }

    public Set<String> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<String> permissoes) {
        this.permissoes = permissoes;
    }
}

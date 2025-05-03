package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;

public class UsuarioPermissaoTelaDTO {
    private int idPermissaoTela;
    private int idTela;  // Usando o ID da Tela
    private int idPermissao;  // Usando o ID da Permissão
    private int idUsuario;  // Usando o ID do Usuário

    public UsuarioPermissaoTelaDTO(UsuarioPermissaoTela usuarioPermissaoTela) {
        this.idPermissaoTela = usuarioPermissaoTela.getIdPermissaoTela();
        this.idTela = usuarioPermissaoTela.getTela().getIdTela();  // Pegando o ID da Tela
        this.idPermissao = usuarioPermissaoTela.getPermissao().getIdPermissao();  // Pegando o ID da Permissão
        this.idUsuario = usuarioPermissaoTela.getUsuario().getIdUsuario();  // Pegando o ID do Usuário
    }

    public UsuarioPermissaoTelaDTO() {
    }

    public int getIdPermissaoTela() {
        return idPermissaoTela;
    }

    public void setIdPermissaoTela(int idPermissaoTela) {
        this.idPermissaoTela = idPermissaoTela;
    }

    public int getIdTela() {
        return idTela;
    }

    public void setIdTela(int idTela) {
        this.idTela = idTela;
    }

    public int getIdPermissao() {
        return idPermissao;
    }

    public void setIdPermissao(int idPermissao) {
        this.idPermissao = idPermissao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}

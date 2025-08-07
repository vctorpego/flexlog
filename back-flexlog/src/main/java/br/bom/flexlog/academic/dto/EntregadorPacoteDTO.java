package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Entregador;

public class EntregadorPacoteDTO {
    private int idUsuario;
    private String nomeUsuario;



    public EntregadorPacoteDTO(Entregador entregador) {
        this.nomeUsuario = entregador.getNomeUsuario();
        this.idUsuario = entregador.getIdUsuario();// método correto para pegar o nome no Entregador
    }


    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}

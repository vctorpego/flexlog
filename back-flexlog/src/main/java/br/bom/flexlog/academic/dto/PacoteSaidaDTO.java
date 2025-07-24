package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.Entregador;

public class PacoteSaidaDTO {
    private EntregadorIdDTO entregador;

    public EntregadorIdDTO getEntregador() {
        return entregador;
    }

    public void setEntregador(EntregadorIdDTO entregador) {
        this.entregador = entregador;
    }
}


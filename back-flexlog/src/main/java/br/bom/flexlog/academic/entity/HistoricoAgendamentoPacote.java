package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class HistoricoAgendamentoPacote implements Serializable {

    @EmbeddedId
    private HistoricoAgendamentoPK chaveComposta;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dtModificacao;

    public HistoricoAgendamentoPacote(Date dtModificacao, HistoricoAgendamentoPK chaveComposta) {
        this.dtModificacao = dtModificacao;
        this.chaveComposta = chaveComposta;
    }

    public HistoricoAgendamentoPacote() {
    }

    public HistoricoAgendamentoPK getChaveComposta() {
        return chaveComposta;
    }

    public void setChaveComposta(HistoricoAgendamentoPK chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public Date getDtModificacao() {
        return dtModificacao;
    }

    public void setDtModificacao(Date dtModificacao) {
        this.dtModificacao = dtModificacao;
    }
}

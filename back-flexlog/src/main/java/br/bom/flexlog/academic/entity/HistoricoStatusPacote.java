package br.bom.flexlog.academic.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class HistoricoStatusPacote  implements Serializable {

    @EmbeddedId
    private HistoricoStatusPK chaveComposta;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dtModificacao;

    public HistoricoStatusPacote(Date dtModificacao, HistoricoStatusPK chaveComposta) {
        this.dtModificacao = dtModificacao;
        this.chaveComposta = chaveComposta;
    }

    public HistoricoStatusPacote() {

    }


    public HistoricoStatusPK getChaveComposta() {
        return chaveComposta;
    }

    public void setChaveComposta(HistoricoStatusPK chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public Date getDtModificacao() {
        return dtModificacao;
    }

    public void setDtModificacao(Date dtModificacao) {
        this.dtModificacao = dtModificacao;
    }
}

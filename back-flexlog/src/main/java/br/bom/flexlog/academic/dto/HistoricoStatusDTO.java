package br.bom.flexlog.academic.dto;

import br.bom.flexlog.academic.entity.HistoricoStatusPacote;
import br.bom.flexlog.academic.entity.StatusPacote;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class HistoricoStatusDTO {

    private StatusPacote status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Date dataStatus;

    public HistoricoStatusDTO(HistoricoStatusPacote entity) {
        this.status = entity.getId().getStatus();
        this.dataStatus = entity.getId().getDataStatus();
    }

    public StatusPacote getStatus() {
        return status;
    }

    public void setStatus(StatusPacote status) {
        this.status = status;
    }

    public Date getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Date dataStatus) {
        this.dataStatus = dataStatus;
    }
}

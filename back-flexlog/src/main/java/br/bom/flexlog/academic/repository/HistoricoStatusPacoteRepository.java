package br.bom.flexlog.academic.repository;

import br.bom.flexlog.academic.entity.HistoricoStatusPK;
import br.bom.flexlog.academic.entity.HistoricoStatusPacote;
import br.bom.flexlog.academic.entity.Pacote;
import br.bom.flexlog.academic.entity.StatusPacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface HistoricoStatusPacoteRepository extends JpaRepository<HistoricoStatusPacote, Integer> {
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM HistoricoStatusPacote h " +
            "WHERE h.id.pacote = :pacote AND h.id.status = :status " +
            "AND FUNCTION('DATE', h.id.dataStatus) = :data")
    boolean existsByPacoteAndStatusAndData(@Param("pacote") Pacote pacote,
                                           @Param("status") StatusPacote status,
                                           @Param("data") Date dataSemHora);


    boolean existsById(HistoricoStatusPK id);

}

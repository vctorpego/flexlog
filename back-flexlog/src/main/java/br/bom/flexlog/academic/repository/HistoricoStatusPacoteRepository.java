package br.bom.flexlog.academic.repository;

import br.bom.flexlog.academic.entity.HistoricoStatusPK;
import br.bom.flexlog.academic.entity.HistoricoStatusPacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoStatusPacoteRepository extends JpaRepository<HistoricoStatusPacote, Integer> {

    boolean existsById(HistoricoStatusPK id);

}

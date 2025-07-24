package br.bom.flexlog.academic.repository;


import br.bom.flexlog.academic.entity.StatusPacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPacoteRepository extends JpaRepository<StatusPacote, Integer> {

}

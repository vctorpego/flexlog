package br.bom.flexlog.academic.repository;


import br.bom.flexlog.academic.entity.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacoteRepository extends JpaRepository<Pacote, Integer> {

    boolean existsByCodigoRastreio(String codigoRastreio);
}

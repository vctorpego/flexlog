package br.bom.flexlog.academic.repository;

import br.bom.flexlog.academic.entity.Tela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelaRepository extends JpaRepository<Tela, Integer> {
    Tela findByNomeTela(String nomeTela);
    Tela findByUrlTela(String urlTela);

}

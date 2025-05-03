package br.bom.flexlog.academic.repository;

import br.bom.flexlog.academic.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {
    Permissao findByAcaoPermissao(String acaoPermissao);

    List<Permissao> findAllByOrderByIdPermisaoAsc();


}


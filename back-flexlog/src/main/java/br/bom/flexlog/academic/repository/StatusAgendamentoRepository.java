package br.bom.flexlog.academic.repository;

import br.bom.flexlog.academic.entity.StatusAgendamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusAgendamentoRepository extends JpaRepository<StatusAgendamento, Integer> {
}

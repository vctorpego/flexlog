package br.bom.flexlog.academic.repository;


import br.bom.flexlog.academic.entity.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacoteRepository extends JpaRepository<Pacote, Integer> {
    @Query("SELECT p FROM Pacote p WHERE p.entregador.id = :idEntregador " +
            "AND p.idPacote NOT IN (" +
            "   SELECT h.id.pacote.idPacote FROM HistoricoStatusPacote h " +
            "   WHERE h.id.status.idStatusPacote IN (3, 4)" +
            ")")
    List<Pacote> findPacotesNaoEntreguesPorEntregador(@Param("idEntregador") int idEntregador);

    @Query("SELECT p FROM Pacote p " +
            "WHERE p.entregador IS NOT NULL " +
            "AND p.ultimoStatus.idStatusPacote = 4")
    List<Pacote> findPacotesComFalhaEntregaAindaComEntregador();



    @Query("SELECT p FROM Pacote p WHERE p.codigoRastreio = :codigoRastreio ")
    Pacote findCodigoRastreio(String codigoRastreio);
    
}

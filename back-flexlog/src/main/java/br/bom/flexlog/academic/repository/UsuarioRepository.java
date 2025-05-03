package br.bom.flexlog.academic.repository;

import br.bom.flexlog.academic.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
// De acordo com o tipo id do Usuario int --> Integer
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);



}

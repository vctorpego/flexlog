package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.TelaComPermissoesDTO;
import br.bom.flexlog.academic.repository.UsuarioRepository;
import br.bom.flexlog.academic.service.UsuarioPermissaoTelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/permissao")
public class UsuarioPermissaoTelaController {

    @Autowired
    private UsuarioPermissaoTelaService usuarioPermissaoTelaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/telas/{idUsuario}")
    public ResponseEntity<List<TelaComPermissoesDTO>> listarTelasComPermissoes(@PathVariable int idUsuario) {
        // Verificar se o usuário existe no banco de dados
        if (!usuarioRepository.existsById(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna "não encontrado" se usuário não existir
        }

        // Recupera as permissões vinculadas ao usuário
        List<TelaComPermissoesDTO> telasComPermissoes = usuarioPermissaoTelaService.listarTelasComPermissoes(idUsuario);

        // Se o usuário não tiver permissões, retorne uma lista vazia
        if (telasComPermissoes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Retorna uma lista vazia em vez de "Not Found"
        }

        return ResponseEntity.ok(telasComPermissoes); // Retorna os dados
    }

}

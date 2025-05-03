package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.entity.Permissao;
import br.bom.flexlog.academic.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping
    public ResponseEntity<List<Permissao>> listarTodas() {
        return ResponseEntity.ok(permissaoService.listarTodasPermissoes());
    }

    @PostMapping
    public ResponseEntity<Permissao> criar(@RequestBody Permissao permissao) {
        Permissao salva = permissaoService.salvarPermissao(permissao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @DeleteMapping("/{idPermissao}")
    public ResponseEntity<Void> deletar(@PathVariable int idPermissao) {
        permissaoService.excluirPermissao(idPermissao);
        return ResponseEntity.noContent().build();
    }
}

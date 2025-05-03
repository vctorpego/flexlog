package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.TelaDTO;
import br.bom.flexlog.academic.service.TelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tela")
public class TelaController {
    @Autowired
    private TelaService telaService;


    @GetMapping
    public List<TelaDTO> listarTodos(){
        return telaService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<TelaDTO> inserir(@RequestBody TelaDTO tela) {
        // Verifica se já existe uma tela com o mesmo nome ou URL
        if (telaService.existeTelaComNomeOuUrl(tela.getNomeTela(), tela.getUrlTela())) {
            return ResponseEntity.status(409).build(); // Retorna status 409 (Conflict) caso o recurso já exista
        }

        // Inserir a nova tela se não houver conflito
        TelaDTO novaTela = telaService.inserir(tela);
        return ResponseEntity.status(201).body(novaTela); // Retorna status 201 (Created) quando a inserção for bem-sucedida
    }

    @PutMapping
    public TelaDTO alterar(@RequestBody TelaDTO tela){
        return telaService.alterar(tela);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id){
        telaService.excluir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelaDTO> buscarPorId(@PathVariable Integer id){
        TelaDTO tela = telaService.buscarPorId(id);
        return tela != null ? ResponseEntity.ok(tela) : ResponseEntity.notFound().build();
    }
}

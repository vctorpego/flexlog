package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.EntregadorDTO;
import br.bom.flexlog.academic.dto.TransportadoraDTO;
import br.bom.flexlog.academic.entity.Pacote;
import br.bom.flexlog.academic.repository.PacoteRepository;
import br.bom.flexlog.academic.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/entregador")
public class EntregadorController {

    @Autowired
    private EntregadorService  entregadorService;
    @Autowired
    private  PacoteRepository pacoteRepository;

    @GetMapping("/pacotes/{id}")
    public ResponseEntity<List<Pacote>> listarPacotesNaoEntregues(@PathVariable int id) {
        List<Pacote> pacotes = pacoteRepository.findPacotesNaoEntreguesPorEntregador(id);
        return ResponseEntity.ok(pacotes);
    }

    @GetMapping
    public List<EntregadorDTO> listarTodos(){
        return entregadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregadorDTO> buscarEntregadorPorId(@PathVariable Integer id) {
        EntregadorDTO entregadorDTO = entregadorService.buscarPorId(id); // Serviço que busca pelo id

        if (entregadorDTO != null) {
            return ResponseEntity.ok(entregadorDTO); // Retorna a conta encontrada
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 caso não encontre
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody EntregadorDTO entregador) {
        try {
            EntregadorDTO dtoSalvo = entregadorService.inserir(entregador);
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<EntregadorDTO> alterar(@PathVariable Integer id, @RequestBody EntregadorDTO entregadorAtualizado) {
        try {
            EntregadorDTO entregadorExistente = entregadorService.buscarPorId(id);

            if (entregadorExistente != null) {

                entregadorAtualizado.setIdUsuario(id);
                EntregadorDTO entregadorAlterado = entregadorService.alterar(entregadorAtualizado);

                return ResponseEntity.ok(entregadorAlterado); // Retorna o produto atualizado
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Produto não encontrado
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // Retorna 404 se o produto não for encontrado
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id){
        entregadorService.excluir(id);
        return ok().build();
    }



}

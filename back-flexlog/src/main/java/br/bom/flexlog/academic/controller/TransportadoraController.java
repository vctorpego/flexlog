package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.TransportadoraDTO;
import br.bom.flexlog.academic.dto.TransportadoraLiteDTO;
import br.bom.flexlog.academic.exeptions.CnpjDuplicadoException;
import br.bom.flexlog.academic.service.TransportadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/transportadora")
public class TransportadoraController {

    @Autowired
    private TransportadoraService transportadoraService;

    @GetMapping
    public List<TransportadoraDTO> listarTodos(){
        return transportadoraService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportadoraDTO> buscarTransportadoraPorId(@PathVariable Integer id) {
        TransportadoraDTO transportadoraDTO = transportadoraService.buscarPorId(id); // Serviço que busca pelo id

        if (transportadoraDTO != null) {
            return ResponseEntity.ok(transportadoraDTO); // Retorna a conta encontrada
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 caso não encontre
        }
    }


    @PostMapping
    public ResponseEntity<String> inserir(@RequestBody TransportadoraDTO transportadora) {
        try {
            transportadoraService.inserir(transportadora);
            return ResponseEntity.status(201).build();
        } catch (CnpjDuplicadoException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<TransportadoraDTO> alterar(@PathVariable Integer id, @RequestBody TransportadoraDTO transportadoraAtualizada) {
        try {
            TransportadoraDTO transportadoraExistente = transportadoraService.buscarPorId(id);

            if (transportadoraExistente != null) {

                transportadoraAtualizada.setIdTransportadora(id);
                TransportadoraDTO transportadoraAlterada = transportadoraService.alterar(transportadoraAtualizada);

                return ResponseEntity.ok(transportadoraAlterada); // Retorna o produto atualizado
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Produto não encontrado
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // Retorna 404 se o produto não for encontrado
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id){
        transportadoraService.excluir(id);
        return ok().build();
    }






}

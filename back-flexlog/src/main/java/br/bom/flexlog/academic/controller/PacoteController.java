package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.PacoteDTO;
import br.bom.flexlog.academic.dto.PacoteRollbackDTO;
import br.bom.flexlog.academic.dto.PacoteSaidaDTO;
import br.bom.flexlog.academic.exeptions.ConflictExeption;
import br.bom.flexlog.academic.exeptions.NotFoundExeption;
import br.bom.flexlog.academic.service.PacoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacotes")
public class PacoteController {

    @Autowired
    private PacoteService pacoteService;


    @GetMapping
    public List<PacoteDTO> listarTodos() {
        return pacoteService.ListarTodos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            PacoteDTO pacote = pacoteService.buscarPorId(id);
            return ResponseEntity.ok(pacote);
        } catch (NotFoundExeption e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
        return ResponseEntity.badRequest().body("Erro ao carregar pacote: " + e.getMessage());
        }
    }


    @GetMapping("/codigo/{codigoRastreio}")
    public ResponseEntity<?> buscarPorCodigoRastreio(@PathVariable String codigoRastreio) {
        try {
            PacoteDTO pacote = pacoteService.buscarporCodigoRastreio(codigoRastreio);
            return ResponseEntity.ok(pacote);
        } catch (NotFoundExeption e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao carregar pacote: " + e.getMessage());
        }
    }

    @GetMapping("/rollback")
    public ResponseEntity<List<PacoteRollbackDTO>> getPacotesComFalhaEntregaAindaComEntregador() {
        List<PacoteRollbackDTO> pacotesDTO = pacoteService.listarPacotesComFalhaEntregaAindaComEntregador();
        return ResponseEntity.ok(pacotesDTO);
    }

    @PostMapping
    public ResponseEntity<?> inserirPacote(@RequestBody PacoteDTO dto) {
        try {
            PacoteDTO pacoteSalvo = pacoteService.inserir(dto);
            return ResponseEntity.ok(pacoteSalvo);
        } catch (ConflictExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao efetuar entrada: " + e.getMessage());
        }
    }

    @PutMapping("/saida/{idPacote}")
    public ResponseEntity<?> efetuarSaida(@PathVariable int idPacote, @RequestBody PacoteSaidaDTO dto) {
        try {
            PacoteDTO pacoteAtualizado = pacoteService.efetuarSaida(idPacote, dto);
            return ResponseEntity.ok(pacoteAtualizado);
        } catch (ConflictExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao efetuar saída: " + e.getMessage());
        }
    }

    @PutMapping("/rollback/{idPacote}")
    public ResponseEntity<Void> rollbackEntrega(
            @PathVariable int idPacote) {

        pacoteService.rollbackEntrega(idPacote);
        return ResponseEntity.ok().build();
    }





}

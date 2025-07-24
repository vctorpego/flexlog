package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.PacoteDTO;
import br.bom.flexlog.academic.dto.PacoteSaidaDTO;
import br.bom.flexlog.academic.dto.UsuarioDTO;
import br.bom.flexlog.academic.service.PacoteService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<?> inserirPacote(@RequestBody PacoteDTO dto) {
        try {
            PacoteDTO pacoteSalvo = pacoteService.inserir(dto);
            return ResponseEntity.ok(pacoteSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/saida/{idPacote}")
    public ResponseEntity<?> efetuarSaida(@PathVariable int idPacote, @RequestBody PacoteSaidaDTO dto) {
        try {
            PacoteDTO pacoteAtualizado = pacoteService.efetuarSaida(idPacote, dto);
            return ResponseEntity.ok(pacoteAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao efetuar saída: " + e.getMessage());
        }
    }


}

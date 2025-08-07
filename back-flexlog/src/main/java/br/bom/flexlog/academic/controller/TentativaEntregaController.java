package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.TentativaEntregaDTO;
import br.bom.flexlog.academic.entity.Entregador;
import br.bom.flexlog.academic.entity.Pacote;
import br.bom.flexlog.academic.entity.TentativaEntrega;
import br.bom.flexlog.academic.repository.EntregadorRepository;
import br.bom.flexlog.academic.repository.PacoteRepository;
import br.bom.flexlog.academic.repository.TentativaEntregaRepository;
import br.bom.flexlog.academic.service.TentativaEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tentativa-entrega")
public class TentativaEntregaController {


    @Autowired
    private TentativaEntregaService tentativaEntregaService;

    @PostMapping("/{idPacote}")
    public ResponseEntity<?> criarTentativaEntrega(@RequestBody TentativaEntregaDTO dto) {
        TentativaEntrega tentativa = tentativaEntregaService.criarTentativaEntrega(dto);

        if (tentativa == null) {
            // Tentativa duplicada no mesmo dia
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma tentativa de entrega com mesmo pacote, status e data.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(tentativa);
    }


}


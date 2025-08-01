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
    private TentativaEntregaRepository tentativaEntregaRepository;

    @Autowired
    private PacoteRepository pacoteRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private TentativaEntregaService tentativaEntregaService;

    @PostMapping("/{idPacote}")
    public ResponseEntity<?> criarTentativaEntrega(
            @PathVariable int idPacote,
            @RequestBody TentativaEntregaDTO dto) {

        TentativaEntrega tentativaSalva = tentativaEntregaService.criarTentativaEntrega(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tentativaSalva);
    }

}


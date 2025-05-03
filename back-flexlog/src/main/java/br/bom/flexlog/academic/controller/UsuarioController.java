package br.bom.flexlog.academic.controller;

import br.bom.flexlog.academic.dto.UsuarioDTO;
import br.bom.flexlog.academic.entity.Usuario;
import br.bom.flexlog.academic.repository.UsuarioPermissaoTelaRepository;
import br.bom.flexlog.academic.service.PermissaoService;
import br.bom.flexlog.academic.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private UsuarioPermissaoTelaRepository usuarioPermissaoTelaRepository;

    @GetMapping
    public List<UsuarioDTO> listarTodos() {
        return usuarioService.ListarTodos();
    }

    @PostMapping
    public ResponseEntity<Usuario> inserir(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        // Converte o DTO para a entidade Usuario (supondo que você tenha um método de conversão)
        Usuario usuario = usuarioService.inserir(usuarioDTO);

        // Retorna a resposta com o usuário recém-criado e o código HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


    //Ao usuario se alterar retornar 409 (O usuario não pode se alterar)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> alterarPorId(
            @PathVariable Integer id,
            @RequestBody UsuarioDTO usuarioDTO
    ) {
        try {
            UsuarioDTO usuarioLogado = usuarioService.getUsuarioLogado();
            UsuarioDTO usuarioEditado = usuarioService.buscarPorId(id);

            // Verifica se o usuário está tentando alterar a si mesmo
            if (usuarioLogado.getIdUsuario() == usuarioEditado.getIdUsuario()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Retorna 409
            }

            // Verifica se o usuário logado é um SuperAdm
            if (!usuarioLogado.getIsSuperAdm()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Retorna 403 - Acesso negado
            }

            // Atualiza o usuário com a flag isAdm e outros dados
            usuarioDTO.setIsAdm(usuarioDTO.getIsAdm()); // Aqui você pode garantir que a flag isAdm está sendo passada

            UsuarioDTO usuarioAtualizado = usuarioService.alterarPorId(id, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping
    public UsuarioDTO alterar(@RequestBody UsuarioDTO usuario) {
        return usuarioService.alterar(usuario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        try {
            UsuarioDTO usuarioLogado = usuarioService.getUsuarioLogado();
            UsuarioDTO usuarioEditado = usuarioService.buscarPorId(id);

            // Verifica se o usuário logado é um SuperAdm
            if (!usuarioLogado.getIsSuperAdm()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Retorna 403 - Acesso negado
            }

            // Verifica se o usuário está tentando excluir a si mesmo
            if (usuarioLogado.getIdUsuario() == usuarioEditado.getIdUsuario()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Retorna 409 - Conflito
            }


            // Chama o serviço para excluir o usuário
            usuarioService.excluir(id);
            return ResponseEntity.ok().build(); // Retorna 200 - Sucesso
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 - Não encontrado
        }
    }


    @Transactional
    @DeleteMapping("/tela/{idUsuario}")
    public ResponseEntity<?> deletarTelasDoUsuario(@PathVariable Long idUsuario) {
        usuarioPermissaoTelaRepository.deleteByUsuario_IdUsuario(idUsuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{idUsuario}/permissao")
    public ResponseEntity<Void> adicionarPermissao(
            @PathVariable int idUsuario,
            @RequestParam int idTela,
            @RequestParam int idPermissao
    ) {
        permissaoService.atribuirPermissao(idUsuario, idTela, idPermissao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{nomeUsuario}")
    public ResponseEntity<Long> obterIdPorNomeUsuario(@PathVariable String nomeUsuario) {
        try {
            Long id = usuarioService.buscarIdPorNomeUsuario(nomeUsuario);
            return ResponseEntity.ok(id); // Retorna o ID do usuário como Long
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}


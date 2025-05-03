package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.UsuarioPermissaoTelaDTO;
import br.bom.flexlog.academic.entity.Permissao;
import br.bom.flexlog.academic.entity.Tela;
import br.bom.flexlog.academic.entity.Usuario;
import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;
import br.bom.flexlog.academic.repository.PermissaoRepository;
import br.bom.flexlog.academic.repository.TelaRepository;
import br.bom.flexlog.academic.repository.UsuarioPermissaoTelaRepository;
import br.bom.flexlog.academic.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissaoService {

    @Autowired
    private UsuarioPermissaoTelaRepository usuarioPermissaoTelaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TelaRepository telaRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Long buscarIdPorNomeUsuario(String login) {
        return usuarioService.buscarIdPorNomeUsuario(login);
    }

    public boolean temPermissao(int idUsuario, String nomeTela, String acaoPermissao) {
        return usuarioPermissaoTelaRepository.existsPermissao(idUsuario, nomeTela, acaoPermissao);
    }

    public void atribuirPermissao(int idUsuario, int idTela, int idPermissao) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
        Tela tela = telaRepository.findById(idTela).orElseThrow();
        Permissao permissao = permissaoRepository.findById(idPermissao).orElseThrow();

        UsuarioPermissaoTela upt = new UsuarioPermissaoTela();
        upt.setUsuario(usuario);
        upt.setTela(tela);
        upt.setPermissao(permissao);

        usuarioPermissaoTelaRepository.save(upt);
    }

    public void removerPermissao(int idUsuario, int idPermissao, int idTela) {
        UsuarioPermissaoTela permissaoTela = usuarioPermissaoTelaRepository
                .findByUsuario_IdUsuarioAndPermissao_IdPermisaoAndTela_IdTela(idUsuario, idPermissao, idTela)
                .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada para os parâmetros fornecidos."));

        usuarioPermissaoTelaRepository.delete(permissaoTela);
    }


    // Método atualizado para retornar uma lista de DTOs
    public List<UsuarioPermissaoTelaDTO> listarPermissoesDoUsuario(int idUsuario) {
        List<UsuarioPermissaoTela> permissoes = usuarioPermissaoTelaRepository.findByUsuarioIdUsuario(idUsuario);

        // Mapeia a lista de entidades para DTOs
        return permissoes.stream()
                .map(UsuarioPermissaoTelaDTO::new) // Mapeamento de entidade para DTO
                .collect(Collectors.toList());
    }

    public List<Permissao> listarTodasPermissoes() {
        return permissaoRepository.findAll();
    }

    public Permissao salvarPermissao(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    public void excluirPermissao(int idPermissao) {
        permissaoRepository.deleteById(idPermissao);
    }
}

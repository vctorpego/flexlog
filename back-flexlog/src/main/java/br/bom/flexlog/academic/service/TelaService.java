package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.TelaDTO;
import br.bom.flexlog.academic.entity.Tela;
import br.bom.flexlog.academic.repository.TelaRepository;
import br.bom.flexlog.academic.repository.UsuarioPermissaoTelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TelaService {
    @Autowired
    private TelaRepository telaRepository;

    @Autowired
    private UsuarioPermissaoTelaRepository usuarioPermissaoTelaRepository;

    public List<TelaDTO> listarTodos() {
        List<Tela> telas = telaRepository.findAll();
        return telas.stream().map(TelaDTO::new).toList();
    }

    public TelaDTO inserir(TelaDTO tela) {
        // Verifica se já existe uma tela com o mesmo nome ou URL
        if (existeTelaComNomeOuUrl(tela.getNomeTela(), tela.getUrlTela())) {
            throw new RuntimeException("Já existe uma tela com esse nome ou URL.");
        }

        Tela telaEntity = new Tela(tela);
        Tela salva = telaRepository.save(telaEntity);

        return new TelaDTO(salva);
    }

    public boolean existeTelaComNomeOuUrl(String nomeTela, String urlTela) {
        // Verifica se já existe uma tela com o mesmo nome ou URL
        return telaRepository.findByNomeTela(nomeTela) != null || telaRepository.findByUrlTela(urlTela) != null;
    }

    public TelaDTO alterar(TelaDTO tela) {
        Tela telaEntity = new Tela(tela);
        return new TelaDTO(telaRepository.save(telaEntity));
    }

    @Transactional
    public void excluir(Integer id) {
        // Antes de excluir a tela, removemos as permissões associadas
        usuarioPermissaoTelaRepository.deleteByTela_IdTela(id);

        // Agora podemos excluir a tela
        Tela tela = telaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tela não encontrada"));
        telaRepository.delete(tela);
    }

    public TelaDTO buscarPorId(Integer id) {
        return new TelaDTO(telaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tela não encontrada")));
    }
}

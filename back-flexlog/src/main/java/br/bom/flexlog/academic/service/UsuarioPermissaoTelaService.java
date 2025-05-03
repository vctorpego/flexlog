package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.TelaComPermissoesDTO;
import br.bom.flexlog.academic.entity.UsuarioPermissaoTela;
import br.bom.flexlog.academic.repository.UsuarioPermissaoTelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class UsuarioPermissaoTelaService {

    @Autowired
    private UsuarioPermissaoTelaRepository usuarioPermissaoTelaRepository;


    public List<TelaComPermissoesDTO> listarTelasComPermissoes(int idUsuario) {
        List<UsuarioPermissaoTela> permissoes = usuarioPermissaoTelaRepository.findByUsuarioIdUsuario(idUsuario);

        // Mapa temporário com chave sendo o nome da tela e valor um par (urlTela, set de permissões)
        Map<String, Map.Entry<String, Set<String>>> agrupado = new HashMap<>();

        for (UsuarioPermissaoTela upt : permissoes) {
            String nomeTela = upt.getTela().getNomeTela();
            String urlTela = upt.getTela().getUrlTela();
            String acao = upt.getPermissao().getAcaoPermissao();

            agrupado.compute(nomeTela, (k, v) -> {
                if (v == null) {
                    Set<String> permissoesSet = new HashSet<>();
                    permissoesSet.add(acao);
                    return new AbstractMap.SimpleEntry<>(urlTela, permissoesSet);
                } else {
                    v.getValue().add(acao);
                    return v;
                }
            });
        }

        return agrupado.entrySet().stream()
                .map(entry -> new TelaComPermissoesDTO(
                        entry.getKey(),
                        entry.getValue().getKey(),      // urlTela
                        entry.getValue().getValue()))   // Set<String> permissoes
                .toList();
    }


}

package br.bom.flexlog.academic.security;

import br.bom.flexlog.academic.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private PermissaoService permissaoService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || permission == null) {
            return false;
        }

        String nomeTela = String.valueOf(targetDomainObject);
        String acao = String.valueOf(permission);

        // Obtém o login do usuário autenticado
        String login = authentication.getName();

        // Busca o ID do usuário pelo nome de login
        Long idUsuario = permissaoService.buscarIdPorNomeUsuario(login);
        if (idUsuario == null) {
            return false;
        }

        // Verifica se o usuário tem permissão para a ação na tela
        return permissaoService.temPermissao(idUsuario.intValue(), nomeTela, acao);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // Caso deseje implementar permissões baseadas em ID (não obrigatório por enquanto)
        return false;
    }
}

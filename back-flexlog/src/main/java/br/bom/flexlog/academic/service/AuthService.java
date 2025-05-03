package br.bom.flexlog.academic.service;

import br.bom.flexlog.academic.dto.AcessDTO;
import br.bom.flexlog.academic.dto.AuthenticationDTO;
import br.bom.flexlog.academic.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public AcessDTO login(AuthenticationDTO authDto){
        try {
            // Cria o token de autenticação
            UsernamePasswordAuthenticationToken userAuth =
                    new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getSenhaUsuario());

            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(userAuth);

            // Se a autenticação for bem-sucedida, gera o token JWT
            UserDetailImpl userAuthenticate = (UserDetailImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
            return new AcessDTO(token);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }
}

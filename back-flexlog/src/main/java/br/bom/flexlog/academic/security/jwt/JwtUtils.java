package br.bom.flexlog.academic.security.jwt;

import br.bom.flexlog.academic.service.UserDetailImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${TechMeal.jwtSecret}")
    private String jwtSecret;
    @Value("${TechMeal.jwtExpirationsMs}")
    private int jwtExpirationMs;




    public String generateTokenFromUserDetailsImpl(UserDetailImpl userDetail){
        return Jwts.builder().setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ jwtExpirationMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();

    }
    public Key getSigninKey() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return key;
    }
    public String getLoginToken(String token){
        return Jwts.parser().setSigningKey(getSigninKey()).build()
                .parseClaimsJws(token).getBody().getSubject();

    }
    public boolean validateJwtToken(String authToken) {
        try {
            // Tenta validar o token usando a chave de assinatura.
            Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(authToken);
            return true; // Se o token for válido, retorna true.
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token nao suportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token invalido: " + e.getMessage());
        } catch (JwtException e) {

            System.err.println("Erro na validacao do JWT: " + e.getMessage());
        }
        return false; // Se ocorrer qualquer exceção, retorna false.
    }




}

package br.com.alura.forum.config.security;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import java.util.Date;

@Service
public class TokenService {


    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Date hoje = new Date();

        return Jwts.builder()
                .setIssuer("API DO FÓRUM DA ALURA")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(new Date(hoje.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValidToken(String token) {

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long extrairIdUsuario(String token) {
        try {
            String id = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
            return Long.parseLong(id);
        } catch (Exception e) {
            return null;
        }
    }
}

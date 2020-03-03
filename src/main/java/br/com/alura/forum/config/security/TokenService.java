package br.com.alura.forum.config.security;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

//    public static void main(String[] args) {
//
//        String secret = "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\\}x3?JR3.2zr~v)gYF^8\\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\\J?N,nvH.<2\\.r~w]*e~vgak)X\"v8H`MH/7\"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/";
//
//        Date hoje = new Date();
//
//        System.out.println(Jwts.builder()
//                .setIssuer("API DO FÓRUM DA ALURA")
//                .setSubject("eoqtrabb")
//                .setIssuedAt(hoje)
//                .setExpiration(new Date(hoje.getTime() + Long.parseLong("145454315158")))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact());
//    }
}

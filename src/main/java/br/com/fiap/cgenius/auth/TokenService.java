package br.com.fiap.cgenius.auth;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.cgenius.domain.model.Atendente;
import br.com.fiap.cgenius.domain.service.AtendenteService;


@Service
public class TokenService {
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("cgenius");
    private final AtendenteService atendenteService;

    public TokenService(AtendenteService atendenteService){
        this.atendenteService = atendenteService;
    }

    public Token createToken(String email){
        var expirationAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
            .withSubject(email)
            .withExpiresAt(expirationAt)
            .withIssuer("cgenius")
            .sign(ALGORITHM);
        return new Token(token, email);
    }

    public Atendente getAtendenteFromToken(String token) {
        var email = JWT.require(ALGORITHM)
                .withIssuer("cgenius")
                .build()
                .verify(token)
                .getSubject();    
        var atendente = atendenteService.findByEmail(email);
        if (atendente != null) {
            return atendente;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }



}

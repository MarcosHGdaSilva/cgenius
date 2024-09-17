package br.com.fiap.cgenius.auth;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.cgenius.domain.model.Atendente;
import br.com.fiap.cgenius.domain.repository.AtendenteRepository;


@Service
public class TokenService {
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("cgenius");
    private final AtendenteRepository atendenteRepository;

    public TokenService(AtendenteRepository atendenteRepository){
        this.atendenteRepository = atendenteRepository;
    }

    public Token createToken(String cpf){
        var expirationAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
            .withSubject(cpf)
            .withExpiresAt(expirationAt)
            .withIssuer("cgenius")
            .sign(ALGORITHM);
        return new Token(token, cpf);
    }

    public Atendente getAtendenteFromToken(String token) {
        var cpf = JWT.require(ALGORITHM)
                .withIssuer("cgenius")
                .build()
                .verify(token)
                .getSubject();    
        var atendente = atendenteRepository.findByCpf(cpf);
        if (atendente != null) {
            return atendente;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}

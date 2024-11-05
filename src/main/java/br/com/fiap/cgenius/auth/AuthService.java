package br.com.fiap.cgenius.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.cgenius.domain.model.Atendente;
import br.com.fiap.cgenius.domain.service.AtendenteService;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final AtendenteService atendenteService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(TokenService tokenService, AtendenteService atendenteService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.atendenteService = atendenteService;
        this.passwordEncoder = passwordEncoder;
    }

    public Token login(Credentials credentials){
        var atendente = atendenteService.findByEmail(credentials.email());
        if(atendente == null){
            throw new RuntimeException("Atendente não encontrado");
        }
        if(passwordEncoder.matches(credentials.senha(), atendente.getSenha())){
            return tokenService.createToken(credentials.email());
        }
        throw new RuntimeException("Senha inválida");
    }

    public Token loginOAuth2(OAuth2User principal){
        var atendente = atendenteService.findByEmail(principal.getAttribute("email"));
        if(atendente == null){
            atendente = new Atendente(principal);
        }
        return tokenService.createToken(atendente.getEmail());
    }

    // public boolean register(Credentials credentials) {
    //     var atendente = atendenteRepository.findByEmail(credentials.email());
    //     if (atendente != null) {
    //         throw new RuntimeException("Atendente já registrado");
    //     }
    //     var newAtendente = new Atendente(credentials.email(), passwordEncoder.encode(credentials.senha()));
    //     atendenteRepository.save(newAtendente);
    //     return true;
    // }


}

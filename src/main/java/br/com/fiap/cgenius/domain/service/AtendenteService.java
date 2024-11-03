package br.com.fiap.cgenius.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cgenius.domain.model.Atendente;
import br.com.fiap.cgenius.domain.repository.AtendenteRepository;

@Service
public class AtendenteService extends DefaultOAuth2UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AtendenteRepository atendenteRepository;

    public AtendenteService(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }
    
    // public Atendente create(OAuth2User principal) {
    //     if (atendenteRepository.findByEmail(principal.getAttribute("email")).isEmpty()) {
    //         return atendenteRepository.save(new Atendente(principal));
            
    //     }
    //     throw new ResponseStatusException(HttpStatus.CONFLICT, "Atendente já cadastrado");
    // }

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        return atendenteRepository.findByEmail(email).orElseGet(
            () -> {
                var atendente = new Atendente(oauth2User);
                return atendenteRepository.save(atendente);
            }
        );
    }

    public List<Atendente> findAll() {
        return atendenteRepository.findAll();
    }

    public Atendente create(Atendente atendente) {
        if (atendenteRepository.findByCpf(atendente.getCpf()) == null) {
            atendente.setSenha(passwordEncoder.encode(atendente.getSenha()));
            return atendenteRepository.save(atendente);
        }else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Atendente já cadastrado");
        }
    }

    public Atendente findById(Long id) {
        return atendenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado atendente com o id: " + id));
    }

    public Atendente findByCpf(String cpf_atendente){
        Atendente atendente = atendenteRepository.findByCpf(cpf_atendente);
        if (atendente != null) {
            return atendente;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendente não encontrado");
        }
    }

    public Atendente findByEmail(String email_atendente){
        return atendenteRepository.findByEmail(email_atendente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendente não encontrado"));
    }

    public Atendente update(Atendente atendente) {
        atendente.setSenha(passwordEncoder.encode(atendente.getSenha()));
        return atendenteRepository.save(atendente);
    }

    public void delete(Long id) {
        verificarExistencia(id);
        atendenteRepository.deleteById(id);
    }

    public void deleteByCpf(String cpf){
        verificarCpf(cpf);
        atendenteRepository.deleteByCpf(cpf);
    }

    public void verificarExistencia(Long id){
        atendenteRepository.
        findById(id)
        .orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado")
        );
    }

    private Atendente verificarCpf(String cpf_atendente){
        Atendente atendente = atendenteRepository.findByCpf(cpf_atendente);
        if (atendente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendente não encontrado");
        }else{
            return atendente;
        }   
    }

    public void saveTemporary(Atendente atendente) {
        atendenteRepository.save(atendente);
    }
    // @Override
    // public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    //     var oauth2User = super.loadUser(userRequest);
    //     String cpf = oauth2User.getAttribute("cpf");
    //     return atendenteRepository.findByCpf(cpf).orElseGet(
    //         () -> {
    //             var atendente = new Atendente(oauth2User);
    //             return atendenteRepository.save(atendente);
    //         }
    //     );
    // }
}
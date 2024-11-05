package br.com.fiap.cgenius.auth;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import br.com.fiap.cgenius.domain.service.AtendenteService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {
    
    private final AtendenteService atendenteService;

    public LoginListener(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        var principal = (OAuth2User) event.getAuthentication().getPrincipal();
        System.out.println("LoginListener.onApplicationEvent() " + principal);
        atendenteService.create(principal);
    }
    

}

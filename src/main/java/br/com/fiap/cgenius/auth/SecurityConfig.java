package br.com.fiap.cgenius.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.fiap.cgenius.domain.service.AtendenteService;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationFilter authorizationFilter, AtendenteService atendenteService) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //         .requestMatchers(HttpMethod.POST, "/login").permitAll()
                //         .requestMatchers(HttpMethod.POST, "/atendente").permitAll()
                //         .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/docs").permitAll()
                //         .requestMatchers(HttpMethod.POST, "/finalizar").permitAll()
                //         .anyRequest().authenticated()
                // .oauth2Login(login -> login
                //                         .loginPage("/oauth2/authorization/google")
                //                         .userInfoEndpoint(userInfo -> userInfo.userService(atendenteService))
                //                         .permitAll())
            ;


        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

package br.com.fiap.cgenius.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public AuthorizationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader("Authorization");
        if (header == null){
            filterChain.doFilter(request, response);
            return;
        }
        // if(!header.startsWith("Bearer ")){
        //     response.setStatus(401);
        //     response.addHeader("Content-Type", "application/json");
        //     response.getWriter().write("""
        //                 {
        //                     "message": "Token deve ser do tipo Bearer"
        //                 }
        //             """);
        //     return;
        // }
        var token = header.replace("Bearer ", "");

        try{
            var atendente = tokenService.getAtendenteFromToken(token);
            var auth = new UsernamePasswordAuthenticationToken(atendente.getCpf(), atendente.getSenha(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.setStatus(403);
            response.addHeader("Content-Type", "application/json");
            response.getWriter().write("""
                {
                    "message": "%s"
                }
            """.formatted(e.getMessage()));

        }
    }
}

package br.com.fiap.cgenius.auth;

import br.com.fiap.cgenius.domain.model.Atendente;

public record AuthResponse(
    Token token, 
    Atendente atendente
) {

    public static AuthResponse from(Token token, Atendente atendente){
        return new AuthResponse(token, atendente);
    }


}
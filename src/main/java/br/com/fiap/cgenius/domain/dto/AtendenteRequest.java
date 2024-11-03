package br.com.fiap.cgenius.domain.dto;

import br.com.fiap.cgenius.domain.model.Atendente;

public record AtendenteRequest(
        String nome,
        String email,
        String cpf,
        String setor,
        String senha,
        String perfil
) {
    public Atendente toModel(){
        return new Atendente(
            null,
            nome,
            email,
            cpf,
            setor,
            senha,
            perfil
            );
    }
    
}

package br.com.fiap.cgenius.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.cgenius.domain.dto.AtendenteRequest;
import br.com.fiap.cgenius.domain.service.AtendenteService;

@RestController
@RequestMapping("/finalizar")
public class ConcluiCadastro {

    @Autowired
    private AtendenteService atendenteService;

    @PostMapping
    public ResponseEntity<?> finalizarCadastro(@RequestBody AtendenteRequest atendenteRequest) {
        var atendente = atendenteService.findByEmail(atendenteRequest.email());
        if (atendente == null) {
            return ResponseEntity.status(404).body("Atendente não encontrado");
        }
        atendente.setNome(atendenteRequest.nome());
        atendente.setCpf(atendenteRequest.cpf());
        atendente.setSetor(atendenteRequest.setor());
        atendente.setSenha(atendenteRequest.senha());
        atendente.setPerfil(atendenteRequest.perfil());
        atendenteService.update(atendente);
        return ResponseEntity.ok("Cadastro finalizado com sucesso");
    }
}

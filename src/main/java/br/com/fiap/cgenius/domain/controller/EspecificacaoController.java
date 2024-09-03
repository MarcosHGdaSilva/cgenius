package br.com.fiap.cgenius.domain.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.cgenius.domain.model.Especificacao;
import br.com.fiap.cgenius.domain.service.EspecificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RequestMapping("/especificacao")
@Slf4j
@CacheConfig(cacheNames = "especificacao")
@Tag(name = "especificacao", description = "Endpoint relacionado com especificacao")
public class EspecificacaoController {
    @Autowired
    EspecificacaoService especificacaoService;

    @GetMapping
    @Operation(summary = "Lista todas as especificacoes cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo especificacao")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de especificacoes retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<Especificacao> index(){
        return especificacaoService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Lista todas as especificacoes cadastradas no sistema.", description = "Endpoint que retorna um array de objetos do tipo especificacao")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Especificacao cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do especificacao"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Especificacao create(@RequestBody Especificacao especificacao){
        log.info("cadastrando especificacao: {}", especificacao);
        return especificacaoService.create(especificacao);
        
    }

    @GetMapping("{id}")
    @Operation(summary = "Retorna uma especificacao especifica cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo especificacao com um id informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especificacao encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Especificacao não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Especificacao> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Especificacao especificacao = especificacaoService.findById(id);
        if (especificacao != null) {
            return ResponseEntity.ok(especificacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("cpf/{cpf_cliente}")
    @Operation(summary = "Retorna uma especificacao especifica cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo especificacao com um cpf informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especificacao encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Especificacao não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Especificacao> get(@PathVariable String cpf_cliente){
        log.info("Buscar por CPF: {}", cpf_cliente);
        Especificacao especificacao = especificacaoService.findByCpf(cpf_cliente);
    if (especificacao != null) {
        return ResponseEntity.ok(especificacao);
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    @GetMapping("idcliente/{id}")
    @Operation(summary = "Retorna uma especificacao especifica cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo especificacao com um id de cliente informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especificacao encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Especificacao não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Especificacao> getIdCliente(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Especificacao especificacao = especificacaoService.findByIdCliente(id);
        if (especificacao != null) {
            return ResponseEntity.ok(especificacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deleta uma especificacao pelo ID.", description = "Endpoint que deleta uma especificacao com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Especificacao deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Especificacao não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void destroy (@PathVariable Long id){
        log.info("Apagando id {}", id);
        especificacaoService.deleteById(id);
    }

    @Transactional
    @DeleteMapping("cpf/{cpf_cliente}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deleta uma especificacao pelo CPF.", description = "Endpoint que deleta uma especificacao com um CPF informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void deleteByCpf_cliente (@PathVariable String cpf_cliente){
        log.info("Apagando Cliente com CPF {}", cpf_cliente);
        especificacaoService.deleteByCpf(cpf_cliente);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza uma especificacao pelo ID.", description = "Endpoint que atualiza uma especificacao com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do cliente"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Especificacao update(@PathVariable Long id, @RequestBody Especificacao especificacao){
        log.info("Atualizando o cadastro do id={} para {}", id, especificacao);
        return especificacaoService.update(id, especificacao);
    }

    @PutMapping("cpf/{cpf_cliente}")
    @Operation(summary = "Atualiza uma especificacao pelo CPF.", description = "Endpoint que atualiza uma especificacao com um CPF informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do cliente"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Especificacao update(@PathVariable String cpf_cliente, @RequestBody Especificacao especificacao){
        log.info("Atualizando o cadastro do pcf={} para {}", cpf_cliente, especificacao);
        return especificacaoService.updateByCpf(cpf_cliente, especificacao);
}



}

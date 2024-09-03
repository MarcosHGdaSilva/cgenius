package br.com.fiap.cgenius.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cgenius.domain.model.Especificacao;
import br.com.fiap.cgenius.domain.repository.EspecificacaoRepository;

@Service
public class EspecificacaoService {
    @Autowired
    EspecificacaoRepository especificacaoRepository;

    public List<Especificacao> findAll(){
        return especificacaoRepository.findAll();
    }

    @Transactional
    public Especificacao create(Especificacao especificacao){
        if (especificacaoRepository.findById(especificacao.getCliente().getId()).isPresent()){
            return especificacaoRepository.save(especificacao);
    }else{
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }
}

    public Especificacao findById(Long id){
        return especificacaoRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Não encontrada Especificacão com o id: " + id));
    }

    public Especificacao findByCpf(String cpfCliente){
        Especificacao cliente = especificacaoRepository.findByCpf(cpfCliente);
    if (cliente != null) {
        return cliente;
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especificacão não encontrado");
    }
    }

    public Especificacao findByIdCliente(Long id){
        Especificacao cliente = especificacaoRepository.findByIdCliente(id);
        if (cliente != null) {
            return cliente;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especificacão não encontrado");
        }
    }

    public void deleteById(Long id){
        verificarId(id);
        especificacaoRepository.deleteById(id);
    }

    public void deleteByCpf(String cpfCliente){
        verificarCpf(cpfCliente);
        especificacaoRepository.deleteByCpf(cpfCliente);
    }

    public Especificacao update(Long id, Especificacao especificacao){
        verificarId(id);
        especificacao.setId(id);
        return especificacaoRepository.save(especificacao);
    }

    public Especificacao updateByCpf(String cpfCliente, Especificacao especificacao){
        Especificacao e = verificarCpf(cpfCliente);
        e.setTipoCartaoCredito(especificacao.getTipoCartaoCredito());
        e.setGastoMensal(especificacao.getGastoMensal());
        e.setRendaMensal(especificacao.getRendaMensal());
        e.setViajaFrequentemente(especificacao.getViajaFrequentemente());
        e.setInteresses(especificacao.getInteresses());
        e.setProfissao(especificacao.getProfissao());
        e.setDependentes(especificacao.getDependentes());
        e.setCliente(especificacao.getCliente());
        return especificacaoRepository.save(e);
}

    public void verificarId(Long id){
        especificacaoRepository.
        findById(id)
        .orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado")
        );
    }

    public Especificacao verificarCpf(String cpfCliente){
        Especificacao especificacao = especificacaoRepository.findByCpf(cpfCliente);
    if (especificacao == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF não encontrado");
    }else{
        return especificacao;
    }
}

}

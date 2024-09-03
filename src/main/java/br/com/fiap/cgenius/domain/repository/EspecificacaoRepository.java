package br.com.fiap.cgenius.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.cgenius.domain.model.Especificacao;

public interface EspecificacaoRepository extends JpaRepository<Especificacao, Long> {
    @Query("SELECT e from Especificacao e WHERE e.cliente.cpf = :cpf")
    Especificacao findByCpf(String cpf);

    @Query("SELECT e from Especificacao e WHERE e.cliente.id = :id")
    Especificacao findByIdCliente(Long id);

    @Transactional
    @Query("DELETE FROM Especificacao e WHERE e.cliente.cpf = :cpf")
    void deleteByCpf(String cpf);

}
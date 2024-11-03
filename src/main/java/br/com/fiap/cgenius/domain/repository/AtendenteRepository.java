package br.com.fiap.cgenius.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cgenius.domain.model.Atendente;

public interface AtendenteRepository extends JpaRepository<Atendente, Long>{
    Atendente findByCpf(String cpf);
    Optional<Atendente> findByEmail(String email);
    void deleteByCpf(String cpf);

}

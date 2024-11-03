package br.com.fiap.cgenius.domain.model;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "atendente")
public class Atendente extends DefaultOAuth2User {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_atendente")
    private Long id;

    @Column(name="nome_atendente")
    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @Column(name="email_atendente")
    @NotBlank(message = "Campo obrigatório")
    private String email;

    @Column(name="cpf_atendente")
    @NotBlank(message = "Campo obrigatório")
    @Size(min=11,  message = "CPF Inválido")
    private String cpf;

    @Column(name="setor")
    @NotBlank(message = "Campo obrigatório")
    private String setor;

    @Column(name="senha")
    @NotBlank(message = "Campo obrigatório")    
    @Size(min = 6, message = "Mínimo de 6 caracteres")
    private String senha;

    @Column(name="perfil_atendente")
    private String perfil;
    
    
    public Atendente(OAuth2User principal) {
        super(List.of(new SimpleGrantedAuthority("USER")), principal.getAttributes(), "email");
        this.email = principal.getAttribute("email");
        this.nome = principal.getAttribute("nome");
    }

    public Atendente(Long id, String nome, String email, String cpf, String setor, String senha, String perfil) {
        super(List.of(new SimpleGrantedAuthority("USER")), null, "email");
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.setor = setor;
        this.senha = senha;
        this.perfil = perfil;
    }
    

}
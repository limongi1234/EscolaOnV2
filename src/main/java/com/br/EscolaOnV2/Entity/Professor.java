package com.br.EscolaOnV2.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.br.EscolaOnV2.Enuns.NivelAcesso;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Transactional
@Data
@Table(name = "tb_Professor")
public class Professor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 12)
    private String senha;

    @Column(nullable = false, unique = true, length = 15)
    private String cpf;

    @Column(nullable = false)
    private NivelAcesso nivelDeAcesso;

    @Column(nullable = false)
    private LocalDateTime dataDeCadastro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonIgnore
    private Curso curso;
}


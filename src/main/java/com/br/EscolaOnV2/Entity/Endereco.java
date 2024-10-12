package com.br.EscolaOnV2.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Transactional
@Data
@Table(name = "tb_Endereco")
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String municipio;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String rua;

    @OneToOne(mappedBy = "endereco")
    private Professor professor;

    @OneToOne(mappedBy = "endereco")
    private Aluno aluno;

    @OneToOne(mappedBy = "endereco")
    private Curso curso;
}


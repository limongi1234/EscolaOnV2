package com.br.EscolaOnV2.Dto;

import com.br.EscolaOnV2.Entity.Curso;
import com.br.EscolaOnV2.Entity.Endereco;

import lombok.Data;

@Data
public class ProfessorDTO {

    private Long id;
    private String nome, senha, cpf,nivelDeAcesso;
    private Endereco endereco;
    private Curso curso;
}

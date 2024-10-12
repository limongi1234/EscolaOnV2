package com.br.EscolaOnV2.Dto;

import com.br.EscolaOnV2.Entity.Aluno;
import com.br.EscolaOnV2.Entity.Curso;
import com.br.EscolaOnV2.Entity.Professor;
import lombok.Data;

@Data
public class EndereçoDTO {

    private String provincia, municipio, bairro, rua;
    private Professor professor;
    private Aluno aluno;
    private Curso curso;
}

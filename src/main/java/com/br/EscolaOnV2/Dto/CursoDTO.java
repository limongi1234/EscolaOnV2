package com.br.EscolaOnV2.Dto;

import com.br.EscolaOnV2.Entity.Endereco;
import com.br.EscolaOnV2.Entity.Professor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CursoDTO {

    @NotBlank
    private String curso;

    private int cargaHoraria;

    @NotBlank
    private String descricao;

    private Professor professor;

    private List<Long> aluno;

    private List<Long> prova;

    private Endereco endereco;
}

package com.br.EscolaOnV2.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tb_Prova")
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomeProva, varianteProva;
    private List<String> perguntasProva = new ArrayList<String>();

    @OneToMany
    @JoinColumn(name = "curso_id")
    private List<Curso> curso;
}

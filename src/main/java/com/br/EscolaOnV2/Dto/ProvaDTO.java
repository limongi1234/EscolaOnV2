package com.br.EscolaOnV2.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProvaDTO {

    private Long id;
    private String nomeProva, varianteProva;
    private List<String> perguntasProva = new ArrayList<String>();
    private List<Long> curso;
}

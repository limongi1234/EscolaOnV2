package com.br.EscolaOnV2.Entity;

import com.br.EscolaOnV2.Enuns.NivelAcesso;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Data
@Table(name = "tb_aluno")
public class Aluno  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String cpf;

    @Column(nullable = false)
    private NivelAcesso niveldeacesso;

    @Column(nullable = false)
    private LocalDateTime dataDeCadastro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToMany(mappedBy = "aluno")
    private List<Curso> curso;

}

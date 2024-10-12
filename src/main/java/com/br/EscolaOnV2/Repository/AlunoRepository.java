package com.br.EscolaOnV2.Repository;

import com.br.EscolaOnV2.Entity.Aluno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsBycpf(String cpf);
}
package com.br.EscolaOnV2.Repository;

import com.br.EscolaOnV2.Entity.Prova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {

}

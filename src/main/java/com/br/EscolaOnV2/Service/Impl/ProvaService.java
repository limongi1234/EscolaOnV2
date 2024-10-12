package com.br.EscolaOnV2.Service.Impl;

import com.br.EscolaOnV2.Entity.Prova;
import com.br.EscolaOnV2.Repository.ProvaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvaService {

	private final ProvaRepository provaRepository;

	private static final Logger log = LoggerFactory.getLogger(ProvaService.class);

	@Autowired
	public ProvaService(ProvaRepository provaRepository) {
		this.provaRepository = provaRepository;
	}

	public Prova save(Prova prova) {
		log.info("Salvando prova: {}", prova);
		return provaRepository.save(prova);
	}

	public List<Prova> listar() {
		log.info("Listando todas as provas");
		return provaRepository.findAll();
	}

	public void deletar(Long id) {
		log.info("Deletando prova com id: {}", id);
		if (provaRepository.existsById(id)) {
			provaRepository.deleteById(id);
			log.info("Prova com id: {} deletada com sucesso", id);
		} else
			log.warn("Prova com id: {} não encontrada para deletar", id);

	}

	public Prova updateProva(Prova prova, Long id) {
		log.info("Atualizando prova com id: {}", id);
		if (provaRepository.existsById(id)) {
			prova.setId(id); // Garante que o ID está correto
			Prova updatedProva = provaRepository.save(prova);
			log.info("Prova atualizada: {}", updatedProva);
			return updatedProva;
		} else {
			log.warn("Prova com id: {} não encontrada para atualização", id);
			throw new IllegalArgumentException("Prova não encontrada para o ID informado.");
		}
	}

	public Prova findById(Long id) {
		log.info("Buscando prova com id: {}", id);
		return provaRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Prova com id: {} não encontrada", id);
					return new IllegalArgumentException("Prova não encontrada para o ID informado.");
				});
	}
}

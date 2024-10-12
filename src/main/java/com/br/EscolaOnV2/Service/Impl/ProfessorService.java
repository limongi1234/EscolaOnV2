package com.br.EscolaOnV2.Service.Impl;

import com.br.EscolaOnV2.Entity.Professor;
import com.br.EscolaOnV2.Repository.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

	private static final Logger log = LoggerFactory.getLogger(ProfessorService.class);  // Variável local log

	private final ProfessorRepository professorRepository;

	// Construtor para injeção de dependência
	@Autowired
	public ProfessorService(ProfessorRepository professorRepository) {
		this.professorRepository = professorRepository;
	}

	/**
	 * Salva um novo professor ou atualiza um professor existente.
	 * @param professor o professor a ser salvo.
	 * @return o professor salvo.
	 */
	public Professor save(Professor professor) {
		log.info("Salvando professor: {}", professor.getNome());  // Uso do logger local
		return professorRepository.save(professor);
	}

	/**
	 * Retorna todos os professores disponíveis.
	 * @return lista de professores.
	 */
	public List<Professor> listar() {
		log.info("Listando todos os professores");  // Uso do logger local
		return professorRepository.findAll();
	}

	/**
	 * Deleta um professor pelo seu ID.
	 * @param id o ID do professor a ser deletado.
	 */
	public void deletar(Long id) {
		log.info("Deletando professor com ID: {}", id);  // Uso do logger local
		professorRepository.deleteById(id);
	}

	/**
	 * Atualiza as informações de um professor existente.
	 * @param professor o professor com as novas informações.
	 * @param id o ID do professor a ser atualizado.
	 * @return o professor atualizado.
	 * @throws IllegalArgumentException se o professor com o ID fornecido não for encontrado.
	 */
	public Professor updateProfessor(Professor professor, Long id) {
		// Verificando se o professor existe antes de tentar atualizar
		Optional<Professor> professorExistente = professorRepository.findById(id);
		if (!professorExistente.isPresent())
			throw new IllegalArgumentException("Professor com ID " + id + " não encontrado.");

		professor.setId(id);  // Atualiza o ID do professor com o ID informado
		log.info("Atualizando professor com ID: {}", id);  // Uso do logger local
		return professorRepository.save(professor);  // Salva o professor atualizado
	}

	/**
	 * Encontra um professor por seu ID.
	 * @param id o ID do professor a ser encontrado.
	 * @return o professor correspondente ao ID fornecido.
	 * @throws IllegalArgumentException se o professor não for encontrado.
	 */
	public Professor findById(Long id) {
		return professorRepository.findById(id)
				.orElseThrow(() -> {
					log.error("Professor com ID {} não encontrado.", id);  // Log de erro quando não encontrar o professor
					return new IllegalArgumentException("Professor com ID " + id + " não encontrado.");
				});
	}
}

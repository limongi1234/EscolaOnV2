package com.br.EscolaOnV2.Service.Impl;

import com.br.EscolaOnV2.Entity.Curso;
import com.br.EscolaOnV2.Repository.CursoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CursoService {

	private final CursoRepository cursoRepository;

	@Autowired
	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	/**
	 * Salva um novo curso ou atualiza um curso existente.
	 * @param curso o curso a ser salvo.
	 * @return o curso salvo.
	 */
	public Curso save(Curso curso) {
		return cursoRepository.save(curso);
	}

	/**
	 * Retorna todos os cursos disponíveis.
	 * @return lista de cursos.
	 */
	public List<Curso> listar() {
		return cursoRepository.findAll();
	}

	/**
     * Deleta um curso pelo seu ID.
     *
     * @param id o ID do curso a ser deletado.
     * @return
     */
	public boolean deletar(Long id) {
		cursoRepository.deleteById(id);
        return false;
    }

	/**
	 * Atualiza as informações de um curso existente.
	 * @param curso o curso com as novas informações.
	 * @param id o ID do curso a ser atualizado.
	 * @return o curso atualizado.
	 * @throws IllegalArgumentException se o curso com o ID fornecido não for encontrado.
	 */
	public Curso updateCurso(Curso curso, Long id) {
		// Verificando se o curso existe antes de tentar atualizar
		Optional<Curso> cursoExistente = cursoRepository.findById(id);
		if (!cursoExistente.isPresent())
			throw new IllegalArgumentException("Curso com ID " + id + " não encontrado.");

		curso.setId(id);  // Atualiza o ID do curso com o ID informado
		return cursoRepository.save(curso);  // Salva o curso atualizado
	}

	/**
	 * Encontra um curso por seu ID.
	 * @param id o ID do curso a ser encontrado.
	 * @return o curso correspondente ao ID fornecido.
	 * @throws IllegalArgumentException se o curso não for encontrado.
	 */
	public Curso findById(Long id) {
		return cursoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Curso com ID " + id + " não encontrado."));
	}
}




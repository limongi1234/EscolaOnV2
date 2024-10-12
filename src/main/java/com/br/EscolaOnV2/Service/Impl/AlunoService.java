package com.br.EscolaOnV2.Service.Impl;

import com.br.EscolaOnV2.Entity.Aluno;
import com.br.EscolaOnV2.Repository.AlunoRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;

	private static final Logger log = LoggerFactory.getLogger(AlunoService.class);

	public Aluno save(Aluno aluno) {
		// Aqui podemos aplicar uma validação ou log, caso necessário.
		log.info("Salvando aluno com CPF: {}", aluno.getCpf());
		return alunoRepository.save(aluno);
	}

	public List<Aluno> listar() {
		// Aplicando stream para transformar ou filtrar, caso necessário.
		List<Aluno> alunos = alunoRepository.findAll();

		// Exemplo de filtragem ou transformação com stream (se necessário)
		return alunos.stream()
				.filter(aluno -> aluno != null) // Filtro para não incluir null, por exemplo
				.toList();
	}

	public void deletar(Long id) {
		log.info("Deletando aluno com ID: {}", id);
		alunoRepository.deleteById(id);
	}

	public Aluno updateAluno(Aluno aluno, Long id) {
		// Verificando se o aluno existe antes de tentar atualizar
		Optional<Aluno> alunoExistente = alunoRepository.findById(id);

		if (alunoExistente.isPresent()) {
			Aluno alunoAtualizado = alunoExistente.get();
			// Atualizando dados do aluno, se necessário
			alunoAtualizado.setNome(aluno.getNome());
			alunoAtualizado.setCpf(aluno.getCpf());
			alunoAtualizado.setEmail(aluno.getEmail());
			// Continue com as outras atualizações necessárias

			log.info("Atualizando aluno com ID: {}", id);
			return alunoRepository.save(alunoAtualizado);
		} else {
			log.warn("Aluno com ID {} não encontrado para atualização.", id);
			return null; // ou lançar uma exceção personalizada
		}
	}

	public boolean existsByCpf(String cpf) {
		// Utilizando o repositório diretamente, a lógica aqui está boa.
		return alunoRepository.existsByCpf(cpf);
	}

	public Aluno findById(Long id) {
		// Melhorando a busca com um Optional para evitar exceções
		Optional<Aluno> aluno = alunoRepository.findById(id);

		if (aluno.isPresent()) return aluno.get();
		else {
			log.warn("Aluno com ID {} não encontrado.", id);
			return null; // ou lançar uma exceção personalizada
		}
	}

    public boolean existsByEmail(@NotBlank(message = "{email.not.blank}") @Email(message = "{email.not.valid}") String email) {
        return false;
    }
}


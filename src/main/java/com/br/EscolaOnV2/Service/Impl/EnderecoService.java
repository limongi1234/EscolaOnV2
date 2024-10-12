package com.br.EscolaOnV2.Service.Impl;

import com.br.EscolaOnV2.Entity.Endereco;
import com.br.EscolaOnV2.Repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EnderecoService {

	private final EnderecoRepository enderecoRepository;

	// Construtor para injeção de dependência
	@Autowired
	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	/**
	 * Salva um novo endereço ou atualiza um endereço existente.
	 * @param endereco o endereço a ser salvo.
	 * @return o endereço salvo.
	 */
	public Endereco save(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	/**
	 * Retorna todos os endereços disponíveis.
	 * @return lista de endereços.
	 */
	public List<Endereco> listar() {
		return enderecoRepository.findAll();
	}

	/**
	 * Deleta um endereço pelo seu ID.
	 * @param id o ID do endereço a ser deletado.
	 */
	public void deletar(Long id) {
		enderecoRepository.deleteById(id);
	}

	/**
	 * Atualiza as informações de um endereço existente.
	 * @param endereco o endereço com as novas informações.
	 * @param id o ID do endereço a ser atualizado.
	 * @return o endereço atualizado.
	 * @throws IllegalArgumentException se o endereço com o ID fornecido não for encontrado.
	 */
	public Endereco updateEndereco(Endereco endereco, Long id) {
		// Verificando se o endereço existe antes de tentar atualizar
		Optional<Endereco> enderecoExistente = enderecoRepository.findById(id);
		if (!enderecoExistente.isPresent())
			throw new IllegalArgumentException("Endereço com ID " + id + " não encontrado.");

		endereco.setId(id);  // Atualiza o ID do endereço com o ID informado
		return enderecoRepository.save(endereco);  // Salva o endereço atualizado
	}

	/**
	 * Encontra um endereço por seu ID.
	 * @param id o ID do endereço a ser encontrado.
	 * @return o endereço correspondente ao ID fornecido.
	 * @throws IllegalArgumentException se o endereço não for encontrado.
	 */
	public Endereco findById(Long id) {
		return enderecoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + id + " não encontrado."));
	}
}


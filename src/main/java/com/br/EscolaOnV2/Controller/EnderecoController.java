package com.br.EscolaOnV2.Controller;

import com.br.EscolaOnV2.Entity.Endereco;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.br.EscolaOnV2.Service.Impl.EnderecoService;
import com.br.EscolaOnV2.Dto.EnderecoDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controlador responsável por gerenciar operações relacionadas a Endereços.
 */
@RestController
@RequestMapping("/Endereco")
@CrossOrigin(origins = "*")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    /**
     * Endpoint para salvar um novo endereço.
     *
     * @param enderecoDTO DTO contendo os dados do endereço.
     * @param request      Objeto HttpServletRequest para contexto adicional.
     * @return ResponseEntity contendo o endereço salvo ou mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<Object> saveEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO,
                                               HttpServletRequest request) {
        try {
            // Criação de um novo objeto Endereco e configuração dos atributos a partir do DTO.
            Endereco endereco = new Endereco();
            endereco.setProvincia(enderecoDTO.getProvincia());
            endereco.setMunicipio(enderecoDTO.getMunicipio());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setRua(enderecoDTO.getRua());

            // Salvar o endereço no banco de dados.
            Endereco savedEndereco = enderecoService.save(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o endereço: " + e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os endereços.
     *
     * @return ResponseEntity contendo a lista de endereços ou mensagem de erro.
     */
    @GetMapping
    public ResponseEntity<List<Endereco>> listar() {
        try {
            // Obter a lista de endereços do serviço.
            List<Endereco> listaEndereco = enderecoService.listar();
            return ResponseEntity.ok(listaEndereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para atualizar um endereço existente.
     *
     * @param endereco Dados atualizados do endereço.
     * @param id       ID do endereço a ser atualizado.
     * @return ResponseEntity contendo o endereço atualizado ou mensagem de erro.
     */
    @PutMapping("/updateEndereco/{id}")
    public ResponseEntity<Object> updateEndereco(@RequestBody @Valid Endereco endereco, @PathVariable Long id) {
        try {
            // Atualizar o endereço com o ID fornecido.
            Endereco updatedEndereco = enderecoService.updateEndereco(endereco, id);
            return ResponseEntity.ok(updatedEndereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar o endereço: " + e.getMessage());
        }
    }

    /**
     * Endpoint para deletar um endereço pelo ID.
     *
     * @param id ID do endereço a ser deletado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @DeleteMapping("/deleteEndereco/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        try {
            // Deletar o endereço com o ID fornecido.
            enderecoService.deletar(id);
            return ResponseEntity.ok("Endereço deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o endereço: " + e.getMessage());
        }
    }
}

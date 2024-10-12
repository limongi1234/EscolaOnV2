package com.br.EscolaOnV2.Controller;

import com.br.EscolaOnV2.Entity.Aluno;
import com.br.EscolaOnV2.Dto.AlunoDTO;
import com.br.EscolaOnV2.Enuns.NivelAcesso;
import com.br.EscolaOnV2.Service.Impl.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Controlador para gerenciar as operações relacionadas a Alunos.
 */
@RestController
@RequestMapping("/Aluno")
@CrossOrigin(origins = "*")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    /**
     * Endpoint para salvar um novo Aluno.
     *
     * @param alunoDTO DTO contendo os dados do Aluno.
     * @param request  Objeto HttpServletRequest para contexto adicional.
     * @return ResponseEntity contendo o Aluno salvo ou mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<Object> saveAluno(@RequestBody @Valid AlunoDTO alunoDTO, HttpServletRequest request) {
        try {
            // Cria uma nova instância de Aluno e define suas propriedades a partir do DTO.
            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTO.getNome());
            aluno.setSenha(alunoDTO.getSenha());
            aluno.setCpf(alunoDTO.getCpf());
            aluno.setEmail(alunoDTO.getEmail());
            aluno.setNiveldeacesso(NivelAcesso.ALUNO);
            aluno.setDataDeCadastro(LocalDateTime.now(ZoneId.of("UTC")));

            // Salva a instância de Aluno no banco de dados.
            Aluno savedAluno = alunoService.save(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o aluno: " + e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Alunos.
     *
     * @return ResponseEntity contendo a lista de Alunos ou mensagem de erro.
     */
    @GetMapping
    public ResponseEntity<List<Aluno>> listar() {
        try {
            // Obtém a lista de todos os Alunos.
            List<Aluno> listaAluno = alunoService.listar();
            return ResponseEntity.ok(listaAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint para atualizar um Aluno existente.
     *
     * @param aluno Dados atualizados do Aluno.
     * @param id    ID do Aluno a ser atualizado.
     * @return ResponseEntity contendo o Aluno atualizado ou mensagem de erro.
     */
    @PutMapping("/updateAluno/{id}")
    public ResponseEntity<Object> updateAluno(@RequestBody @Valid Aluno aluno, @PathVariable Long id) {
        try {
            // Atualiza o Aluno com o ID fornecido.
            Aluno updatedAluno = alunoService.updateAluno(aluno, id);
            return ResponseEntity.ok(updatedAluno);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar o aluno: " + e.getMessage());
        }
    }

    /**
     * Endpoint para deletar um Aluno pelo ID.
     *
     * @param id ID do Aluno a ser deletado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @DeleteMapping("/deleteAluno/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        try {
            // Deleta o Aluno com o ID fornecido.
            alunoService.deletar(id);
            return ResponseEntity.ok("Aluno deletado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o aluno: " + e.getMessage());
        }
    }
}

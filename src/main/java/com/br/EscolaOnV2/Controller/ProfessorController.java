package com.br.EscolaOnV2.Controller;

import com.br.EscolaOnV2.Dto.ProfessorDTO;
import com.br.EscolaOnV2.Entity.Professor;
import com.br.EscolaOnV2.Service.Impl.ProfessorService;
import com.br.EscolaOnV2.Service.Impl.ProvaService;
import com.br.EscolaOnV2.Enuns.NivelAcesso;
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
 * Controlador para gerenciar as operações relacionadas a Professores.
 */
@RestController
@RequestMapping("/Professor")
@CrossOrigin(origins = "*")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProvaService provaService;

    /**
     * Endpoint para salvar um novo Professor.
     *
     * @param professorDTO DTO contendo os dados do Professor.
     * @param request       Objeto HttpServletRequest para contexto adicional.
     * @return ResponseEntity contendo o Professor salvo ou mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<Object> saveProfessor(@RequestBody @Valid ProfessorDTO professorDTO,
                                                HttpServletRequest request) {
        try {
            // Cria uma nova instância de Professor e define suas propriedades a partir do DTO.
            Professor professor = new Professor();
            professor.setNome(professorDTO.getNome());
            professor.setSenha(professorDTO.getSenha());
            professor.setCpf(professorDTO.getCpf());
            professor.setNivelDeAcesso(NivelAcesso.PROFESSOR);
            professor.setDataDeCadastro(LocalDateTime.now(ZoneId.of("UTC")));

            // Salva a instância de Professor no banco de dados.
            Professor savedProfessor = professorService.save(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o professor: " + e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Professores.
     *
     * @return ResponseEntity contendo a lista de Professores ou mensagem de erro.
     */
    @GetMapping
    public ResponseEntity<List<Professor>> listar() {
        try {
            // Obtém a lista de todos os Professores.
            List<Professor> listaProfessor = professorService.listar();
            return ResponseEntity.ok(listaProfessor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint para atualizar um Professor existente.
     *
     * @param professor Dados atualizados do Professor.
     * @param id        ID do Professor a ser atualizado.
     * @return ResponseEntity contendo o Professor atualizado ou mensagem de erro.
     */
    @PutMapping("/updateProfessor/{id}")
    public ResponseEntity<Object> updateProfessor(@RequestBody @Valid Professor professor, @PathVariable Long id) {
        try {
            // Atualiza o Professor com o ID fornecido.
            Professor updatedProfessor = professorService.updateProfessor(professor, id);
            return ResponseEntity.ok(updatedProfessor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar o professor: " + e.getMessage());
        }
    }

    /**
     * Endpoint para deletar um Professor pelo ID.
     *
     * @param id ID do Professor a ser deletado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @DeleteMapping("/deleteProfessor/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        try {
            // Deleta o Professor com o ID fornecido.
            professorService.deletar(id);
            return ResponseEntity.ok("Professor deletado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o professor: " + e.getMessage());
        }
    }
}

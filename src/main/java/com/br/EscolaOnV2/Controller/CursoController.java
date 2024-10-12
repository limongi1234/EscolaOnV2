package com.br.EscolaOnV2.Controller;

import com.br.EscolaOnV2.Dto.CursoDTO;
import com.br.EscolaOnV2.Entity.Aluno;
import com.br.EscolaOnV2.Entity.Curso;
import com.br.EscolaOnV2.Entity.Prova;
import com.br.EscolaOnV2.Service.Impl.AlunoService;
import com.br.EscolaOnV2.Service.Impl.CursoService;
import com.br.EscolaOnV2.Service.Impl.ProvaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Curso")
@CrossOrigin(origins = "*")
@Slf4j
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProvaService provaService;

    /**
     * Criação de um novo curso com seus alunos e provas.
     * @param cursoDTO Dados para o cadastro do curso.
     * @param request Requisição HTTP.
     * @return ResponseEntity com status e corpo de resposta.
     */
    @PostMapping
    public ResponseEntity<Object> saveCurso(@RequestBody @Valid CursoDTO cursoDTO, HttpServletRequest request) {
        try {
            Curso curso = new Curso();
            curso.setCurso(cursoDTO.getCurso());
            curso.setDescricao(cursoDTO.getDescricao());
            curso.setDataCadastroCurso(LocalDateTime.now(ZoneId.of("UTC")));
            curso.setTempoAula(cursoDTO.getCargaHoraria());

            // Adicionando alunos ao curso
            List<Aluno> alunos = new ArrayList<>();
            for (Long alunoId : cursoDTO.getAluno()) {
                Aluno aluno = alunoService.findById(alunoId);
                if (aluno != null) alunos.add(aluno);
                 else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno com ID " + alunoId + " não encontrado.");

            }

            // Adicionando provas ao curso
            List<Prova> provas = new ArrayList<>();
            for (Long provaId : cursoDTO.getProva()) {
                Prova prova = provaService.findById(provaId);
                if (prova != null) provas.add(prova);
                else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prova com ID " + provaId + " não encontrada.");

            }

            curso.setAlunos(alunos);
            curso.setProvas(provas);

            // Salvando curso
            curso = cursoService.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(curso);

        } catch (Exception e) {
            // Log de erro (melhor usar algum logger para capturar o erro)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao criar o curso: " + e.getMessage());
        }
    }

    /**
     * Listar todos os cursos cadastrados.
     * @return ResponseEntity com a lista de cursos.
     */
    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        try {
            List<Curso> listaCurso = this.cursoService.listar();
            if (listaCurso.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            return ResponseEntity.ok(listaCurso);
        } catch (Exception e) {
            // Log de erro (melhor usar algum logger para capturar o erro)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar os cursos: " + e.getMessage());
        }
    }

    /**
     * Atualizar os dados de um curso.
     * @param curso Dados atualizados do curso.
     * @param id ID do curso a ser atualizado.
     * @return ResponseEntity com o curso atualizado ou mensagem de erro.
     */
    @PutMapping("/updateCurso/{id}")
    public ResponseEntity<Object> updateCurso(@RequestBody Curso curso, @PathVariable Long id) {
        try {
            Curso cursoAtualizado = cursoService.updateCurso(curso, id);
            return ResponseEntity.ok(cursoAtualizado);
        } catch (Exception e) {
            // Log de erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso com ID " + id + " não encontrado ou erro ao atualizar: " + e.getMessage());
        }
    }

    /**
     * Deletar um curso pelo ID.
     * @param id ID do curso a ser deletado.
     * @return ResponseEntity com status de sucesso ou falha.
     */
    @DeleteMapping("/deleteCurso/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        try {
            boolean deleted = cursoService.deletar(id);
            if (!deleted)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso com ID " + id + " não encontrado.");

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Log de erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar curso: " + e.getMessage());
        }
    }
}

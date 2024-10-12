package com.br.EscolaOnV2.Controller;

import com.br.EscolaOnV2.Dto.AlunoDTO;
import com.br.EscolaOnV2.Dto.CursoDTO;
import com.br.EscolaOnV2.Dto.EnderecoDTO;
import com.br.EscolaOnV2.Dto.ProfessorDTO;
import com.br.EscolaOnV2.Dto.ProvaDTO;
import com.br.EscolaOnV2.Entity.Aluno;
import com.br.EscolaOnV2.Entity.Curso;
import com.br.EscolaOnV2.Entity.Endereco;
import com.br.EscolaOnV2.Entity.Professor;
import com.br.EscolaOnV2.Entity.Prova;
import com.br.EscolaOnV2.Enuns.NivelAcesso;
import com.br.EscolaOnV2.Service.Impl.AlunoService;
import com.br.EscolaOnV2.Service.Impl.CursoService;
import com.br.EscolaOnV2.Service.Impl.EnderecoService;
import com.br.EscolaOnV2.Service.Impl.ProfessorService;
import com.br.EscolaOnV2.Service.Impl.ProvaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Escola")
@CrossOrigin(origins = "*")
public class EscolaController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProvaService provaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private EnderecoService enderecoService;

    // Aluno Operations
    @PostMapping("/Aluno")
    public ResponseEntity<Object> saveAluno(@RequestBody @Valid AlunoDTO alunoDTO) {
        try {
            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTO.getNome());
            aluno.setSenha(alunoDTO.getSenha());
            aluno.setCpf(alunoDTO.getCpf());
            aluno.setEmail(alunoDTO.getEmail());
            aluno.setNiveldeacesso(NivelAcesso.ALUNO);
            aluno.setDataDeCadastro(LocalDateTime.now(ZoneId.of("UTC")));

            Aluno savedAluno = alunoService.save(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o aluno: " + e.getMessage());
        }
    }

    @GetMapping("/Aluno")
    public ResponseEntity<List<Aluno>> listarAlunos() {
        try {
            List<Aluno> listaAluno = alunoService.listar();
            return ResponseEntity.ok(listaAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/Aluno/{id}")
    public ResponseEntity<Object> updateAluno(@RequestBody @Valid Aluno aluno, @PathVariable Long id) {
        try {
            Aluno updatedAluno = alunoService.updateAluno(aluno, id);
            return ResponseEntity.ok(updatedAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar o aluno: " + e.getMessage());
        }
    }

    @DeleteMapping("/Aluno/{id}")
    public ResponseEntity<String> deleteAluno(@PathVariable("id") Long id) {
        try {
            alunoService.deletar(id);
            return ResponseEntity.ok("Aluno deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o aluno: " + e.getMessage());
        }
    }

    // Professor Operations
    @PostMapping("/Professor")
    public ResponseEntity<Object> saveProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        try {
            Professor professor = new Professor();
            professor.setNome(professorDTO.getNome());
            professor.setSenha(professorDTO.getSenha());
            professor.setCpf(professorDTO.getCpf());
            professor.setNivelDeAcesso(NivelAcesso.PROFESSOR);
            professor.setDataDeCadastro(LocalDateTime.now(ZoneId.of("UTC")));

            Professor savedProfessor = professorService.save(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o professor: " + e.getMessage());
        }
    }

    @GetMapping("/Professor")
    public ResponseEntity<List<Professor>> listarProfessores() {
        try {
            List<Professor> listaProfessor = professorService.listar();
            return ResponseEntity.ok(listaProfessor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/Professor/{id}")
    public ResponseEntity<Object> updateProfessor(@RequestBody @Valid Professor professor, @PathVariable Long id) {
        try {
            Professor updatedProfessor = professorService.updateProfessor(professor, id);
            return ResponseEntity.ok(updatedProfessor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar o professor: " + e.getMessage());
        }
    }

    @DeleteMapping("/Professor/{id}")
    public ResponseEntity<String> deleteProfessor(@PathVariable("id") Long id) {
        try {
            professorService.deletar(id);
            return ResponseEntity.ok("Professor deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o professor: " + e.getMessage());
        }
    }

    // Prova Operations
    @PostMapping("/Prova")
    public ResponseEntity<Object> saveProva(@RequestBody @Valid ProvaDTO provaDTO) {
        try {
            if (provaDTO.getPerguntasProva().size() > 100) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A quantidade de questões não pode ser maior que 100.");
            }

            Prova prova = new Prova();
            prova.setNomeProva(provaDTO.getNomeProva());
            prova.setVarianteProva(provaDTO.getVarianteProva());
            prova.setPerguntasProva(provaDTO.getPerguntasProva());

            Prova savedProva = provaService.save(prova);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a prova: " + e.getMessage());
        }
    }

    @GetMapping("/Prova")
    public ResponseEntity<List<Prova>> listarProvas() {
        try {
            List<Prova> listaProva = provaService.listar();
            return ResponseEntity.ok(listaProva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/Prova/{id}")
    public ResponseEntity<Object> updateProva(@RequestBody @Valid Prova prova, @PathVariable Long id) {
        try {
            Prova updatedProva = provaService.updateProva(prova, id);
            return ResponseEntity.ok(updatedProva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prova não encontrada para o id: " + id);
        }
    }

    @DeleteMapping("/Prova/{id}")
    public ResponseEntity<Object> deleteProva(@PathVariable("id") Long id) {
        try {
            boolean deleted = provaService.deletar(id);
            if (deleted) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prova não encontrada para o id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a prova: " + e.getMessage());
        }
    }

    // Curso Operations
    @PostMapping("/Curso")
    public ResponseEntity<Object> saveCurso(@RequestBody @Valid CursoDTO cursoDTO) {
        try {
            Curso curso = new Curso();
            curso.setCurso(cursoDTO.getCurso());
            curso.setDescricao(cursoDTO.getDescricao());
            curso.setDataCadastroCurso(LocalDateTime.now(ZoneId.of("UTC")));
            curso.setTempoAula(cursoDTO.getCargaHoraria());

            List<Aluno> alunos = new ArrayList<>();
            for (Long alunoId : cursoDTO.getAluno()) {
                Aluno aluno = alunoService.findById(alunoId);
                if (aluno != null) alunos.add(aluno);
                else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Aluno com ID " + alunoId + " não encontrado.");
            }

            List<Prova> provas = new ArrayList<>();
            for (Long provaId : cursoDTO.getProva()) {
                Prova prova = provaService.findById(provaId);
                if (prova != null) provas.add(prova);
                else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Prova com ID " + provaId + " não encontrada.");
            }

            curso.setAlunos(alunos);
            curso.setProvas(provas);
            curso = cursoService.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(curso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o curso: " + e.getMessage());
        }
    }

    @GetMapping("/Curso")
    public ResponseEntity<List<Curso>> listarCursos() {
        try {
            List<Curso> listaCurso = cursoService.listar();
            return ResponseEntity.ok(listaCurso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/Curso/{id}")
    public ResponseEntity<Object> updateCurso(@RequestBody Curso curso, @PathVariable Long id) {
        try {
            Curso updatedCurso = cursoService.updateCurso(curso, id);
            return ResponseEntity.ok(updatedCurso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar o curso: " + e.getMessage());
        }
    }

    @DeleteMapping("/Curso/{id}")
    public ResponseEntity<Object> deleteCurso(@PathVariable("id") Long id) {
        try {
            boolean deleted = cursoService.deletar(id);
            if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar curso: " + e.getMessage());
        }
    }

    // Endereco Operations
    @PostMapping("/Endereco")
    public ResponseEntity<Object> saveEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO) {

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

    @GetMapping("/Endereco")
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        try {
            List<Endereco> listaEndereco = enderecoService.listar();
            return ResponseEntity.ok(listaEndereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/Endereco/{id}")
    public ResponseEntity<Object> updateEndereco(@RequestBody Endereco endereco, @PathVariable Long id) {
        try {
            Endereco updatedEndereco = enderecoService.updateEndereco(endereco, id);
            return ResponseEntity.ok(updatedEndereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    @DeleteMapping("/Endereco/{id}")
    public ResponseEntity<Object> deleteEndereco(@PathVariable("id") Long id) {
        try {
            boolean deleted = enderecoService.deletar(id);
            if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar endereço: " + e.getMessage());
        }
    }
}


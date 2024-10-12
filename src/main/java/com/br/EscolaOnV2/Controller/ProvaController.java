package com.br.EscolaOnV2.Controller;

import com.br.EscolaOnV2.Dto.ProvaDTO;
import com.br.EscolaOnV2.Entity.Prova;
import com.br.EscolaOnV2.Service.Impl.ProvaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Prova")
@CrossOrigin(origins = "*")
public class ProvaController {

    @Autowired
    private ProvaService provaService;

    @PostMapping
    public ResponseEntity<Object> saveProva(@RequestBody @Valid ProvaDTO provaDTO) {
        try {
            // Validando a quantidade de perguntas
            if (provaDTO.getPerguntasProva().size() > 100) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A quantidade de questões não pode ser maior que 100.");
            }

            // Criando e configurando a prova
            Prova prova = new Prova();
            prova.setNomeProva(provaDTO.getNomeProva());
            prova.setVarianteProva(provaDTO.getVarianteProva());
            prova.setPerguntasProva(provaDTO.getPerguntasProva());

            // Salvando a prova
            Prova savedProva = provaService.save(prova);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProva);
        } catch (Exception e) {
            // Tratamento de exceções genéricas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a prova: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Prova>> listar() {
        try {
            List<Prova> listaProva = provaService.listar();
            return ResponseEntity.ok(listaProva);
        } catch (Exception e) {
            // Tratamento de exceções para o método de listar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/updateProva/{id}")
    public ResponseEntity<Object> updateProva(@RequestBody @Valid Prova prova, @PathVariable Long id) {
        try {
            Prova updatedProva = provaService.updateProva(prova, id);
            return ResponseEntity.ok(updatedProva);
        } catch (Exception e) {
            // Tratamento de exceções para atualização
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prova não encontrada para o id: " + id);
        }
    }

    @DeleteMapping("/deleteProva/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        try {
            boolean deleted = provaService.deletar(id);
            if (deleted)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Prova não encontrada para o id: " + id);

        } catch (Exception e) {
            // Tratamento de exceções para deletação
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a prova: " + e.getMessage());
        }
    }
}

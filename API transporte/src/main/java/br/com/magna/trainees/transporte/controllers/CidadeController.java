package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.models.CidadeModel;
import br.com.magna.trainees.transporte.services.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Cidade", description = "Api para gerenciamento de Cidades no sistema")
@RestController
@RequestMapping("/cidade")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CidadeController extends GenericController {

    final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @Operation(summary = "Lista todas as cidades", description = "Lista todas as cidades do sistema")
    @ApiResponse(responseCode = "200", description = "Cidades encontradas com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CidadeModel.class))))
    @GetMapping("/")
    public ResponseEntity<Object> get() {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.getAll());
    }

    @Operation(summary = "Recupera uma cidade por ID", description = "Recupera os dados de uma cidade a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Cidade encontrada com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CidadeModel.class))))
    @ApiResponse(responseCode = "404", description = "Cidade não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<CidadeModel> getById(@PathVariable Long id){
        Optional<CidadeModel> optional = cidadeService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Recupera uma cidade pelo nome", description = "Recupera os dados de uma cidade a partir do seu nome")
    @ApiResponse(responseCode = "200", description = "Cidade encontrada com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CidadeModel.class))))
    @ApiResponse(responseCode = "404", description = "Cidade não encontrado")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<CidadeModel> getByCidade(@PathVariable String nome){
        CidadeModel cidade = cidadeService.findBynome(nome);
        return cidade == null ? ResponseEntity.notFound().build()
                : ResponseEntity.status(HttpStatus.OK).body(cidade);
    }

}

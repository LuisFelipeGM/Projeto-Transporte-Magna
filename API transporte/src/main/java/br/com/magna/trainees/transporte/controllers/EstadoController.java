package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.models.EstadoModel;
import br.com.magna.trainees.transporte.services.EstadoService;
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

@Tag(name = "Estado", description = "Api para gerenciamento de Estados no sistema")
@RestController
@RequestMapping("/estado")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstadoController extends GenericController{

    final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @Operation(summary = "Lista todos os estados", description = "Lista todos os estados do sistema")
    @ApiResponse(responseCode = "200", description = "Estados encontrados com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoModel.class))))
    @GetMapping("/")
    public ResponseEntity<Object> get(){
        return ResponseEntity.status(HttpStatus.OK).body(estadoService.getAll());
    }

    @Operation(summary = "Recupera um estado por ID", description = "Recupera os dados de um estado a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Estado encontrado com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoModel.class))))
    @ApiResponse(responseCode = "404", description = "Estado não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> getById(@PathVariable Long id){
        Optional<EstadoModel> optional = estadoService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Recupera um estado pelo nome", description = "Recupera os dados de um estado a partir do seu nome")
    @ApiResponse(responseCode = "200", description = "Estado encontrado com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoModel.class))))
    @ApiResponse(responseCode = "404", description = "Estado não encontrado")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<EstadoModel> getByEstado(@PathVariable String nome){
        EstadoModel estado = estadoService.findBynome(nome);
        return estado == null ? ResponseEntity.notFound().build()
                : ResponseEntity.status(HttpStatus.OK).body(estado);
    }

    @Operation(summary = "Recupera um estado pela sigla", description = "Recupera os dados de um estado a partir de sua sigla")
    @ApiResponse(responseCode = "200", description = "Estado encontrado com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstadoModel.class))))
    @ApiResponse(responseCode = "404", description = "Estado não encontrado")
    @GetMapping("/sigla/{sigla}")
    public ResponseEntity<EstadoModel> getBySigla(@PathVariable String sigla){
        EstadoModel estado = estadoService.findBysigla(sigla);
        return estado == null ? ResponseEntity.notFound().build()
                : ResponseEntity.status(HttpStatus.OK).body(estado);
    }

}

package br.com.magna.trainees.transporte.controllers;


import br.com.magna.trainees.transporte.dtos.LinhaDto;
import br.com.magna.trainees.transporte.models.LinhaModel;
import br.com.magna.trainees.transporte.services.LinhaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Linha", description = "Api para gerenciamento de Linhas no sistema")
@RestController
@RequestMapping("/linha")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LinhaController extends GenericController {

    final LinhaService linhaService;

    public LinhaController(LinhaService linhaService) {
        this.linhaService = linhaService;
    }

    @Operation(summary = "Lista todas as linhas", description = "Lista todas as linhas do sistema")
    @ApiResponse(responseCode = "200", description = "Linhas encontradas com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LinhaModel.class))))
    @GetMapping("/")
    public ResponseEntity<Object> get(){
        return ResponseEntity.status(HttpStatus.OK).body(linhaService.getAll());
    }

    @Operation(summary = "Recupera uma linha por ID", description = "Recupera os dados de uma linha a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Linha encontrada com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LinhaModel.class))))
    @ApiResponse(responseCode = "404", description = "Linha não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<LinhaModel> getById(@PathVariable Long id){
        Optional<LinhaModel> optional = linhaService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salva a linha", description = "Salva a linha")
    @ApiResponse(responseCode = "201", description = "Linha salva com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LinhaModel.class)))
    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody LinhaDto linhaDto, BindingResult result){
        try {
            return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
                    : ResponseEntity.status(HttpStatus.CREATED).body(linhaService.adicionaLinha(linhaDto));
        } catch (Exception e) {
            return handleErrors(e);
        }
    }

    @Operation(summary = "Exclui uma linha pelo Id" , description = "Exclui uma linha a partir do seu ID")
    @ApiResponse(responseCode = "204", description = "Linha excluida com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        Optional<LinhaModel> optionalLinha = linhaService.findById(id);
        return optionalLinha
                .map(linha -> {
                    try {
                        linhaService.deleteById(id);
                        return ResponseEntity.noContent().build();
                    } catch (Exception e) {
                        return handleErrors(e);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Altera uma linha pelo Id" , description = "Altera uma linha a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Linha alterada com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody LinhaDto linhaDto, BindingResult result){
    	Optional<LinhaModel> optionalLinha = linhaService.findById(id);
    	if(optionalLinha.isEmpty())
    			return ResponseEntity.notFound().build();
        try {
            return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
                    : ResponseEntity.status(HttpStatus.CREATED).body(linhaService.putLinha(linhaDto, id));
        } catch (Exception e) {
            return handleErrors(e);
        }
    }

}

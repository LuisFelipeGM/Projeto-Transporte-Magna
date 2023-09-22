package br.com.magna.trainees.transporte.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.services.PassageiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Passageiro", description = "Api para gerenciamento de Passageiros no sistema")
@RestController
@RequestMapping("/passageiro")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PassageiroController extends GenericController {

    final PassageiroService passageiroService;

    public PassageiroController(PassageiroService passageiroService){
        this.passageiroService = passageiroService;
    }
    
    @Operation(summary = "Lista todos os passageiros", description = "Lista todos os passageiros do sistema")
    @ApiResponse(responseCode = "200", description = "Passageiros encontrados com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PassageiroModel.class))))
    @GetMapping("/")
    public ResponseEntity<Object> get(){
        return ResponseEntity.status(HttpStatus.OK).body(passageiroService.getAll());
    }

    @Operation(summary = "Recupera um passageiro por ID", description = "Recupera os dados de um passageiro a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Passageiro encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassageiroModel.class)))
    @ApiResponse(responseCode = "404", description = "Passageiro não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<PassageiroModel> geyById(@PathVariable Long id){
        Optional<PassageiroModel> optional = passageiroService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Salva o passageiro", description = "Salva o passageiro")
    @ApiResponse(responseCode = "201", description = "Passageiro salvo com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassageiroModel.class)))
    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody PassageiroDto passageiroDto, BindingResult result){
        try {
            return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
                    : ResponseEntity.status(HttpStatus.CREATED).body(passageiroService.adicionaPassageiro(passageiroDto));
        } catch (Exception e) {
            return handleErrors(e);
        }
    }

    @Operation(summary = "Exclui um passageiro pelo Id" , description = "Exclui um passageiro a partir do seu ID")
    @ApiResponse(responseCode = "204", description = "Passageiro excluido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        Optional<PassageiroModel> optionalPassageiro = passageiroService.findById(id);
        return optionalPassageiro
                .map(passageiro -> {
                    try {
                        passageiroService.deleteById(id);
                        return ResponseEntity.noContent().build();
                    } catch (Exception e) {
                        return handleErrors(e);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Altera um passageiro pelo Id" , description = "Altera um passageiro a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Passageiro alterado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody PassageiroDto passageiroDto, BindingResult result){
        Optional<PassageiroModel> optionalPassageiro = passageiroService.findById(id);
        if (optionalPassageiro.isEmpty())
                return ResponseEntity.notFound().build();
        try {
            return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
                    : ResponseEntity.status(HttpStatus.CREATED).body(passageiroService.putPassageiro(passageiroDto, id));
        } catch (Exception e) {
            return handleErrors(e);
        }
    }

    
}

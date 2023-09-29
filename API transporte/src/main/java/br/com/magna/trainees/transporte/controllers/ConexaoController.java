package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.dtos.ConexaoDto;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.ConexaoModel;
import br.com.magna.trainees.transporte.services.ConexaoService;
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

@Tag(name = "Conexão", description = "Api para gerenciamento de Conexões no sistema")
@RestController
@RequestMapping("/conexao")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConexaoController extends GenericController {

    final ConexaoService conexaoService;

    public ConexaoController(ConexaoService conexaoService) {
        this.conexaoService = conexaoService;
    }

    @Operation(summary = "Lista todos as conexões", description = "Lista todas as conexões do sistema")
    @ApiResponse(responseCode = "200", description = "Conexões encontradas com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ConexaoModel.class))))
    @GetMapping("/")
    public ResponseEntity<Object> get() {
        return ResponseEntity.status(HttpStatus.OK).body(conexaoService.getAll());
    }

    @Operation(summary = "Recupera uma conexão por ID", description = "Recupera os dados de uma conexão a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Conexão encontrada com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ConexaoModel.class))))
    @ApiResponse(responseCode = "404", description = "Conexão não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ConexaoModel> getById(@PathVariable Long id){
        Optional<ConexaoModel> optional = conexaoService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salva a Conexão", description = "Salva a Conexão")
    @ApiResponse(responseCode = "201", description = "Conexão salva com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoModel.class)))
    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody ConexaoDto conexaoDto, BindingResult result){
        try {
            return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
                    : ResponseEntity.status(HttpStatus.CREATED).body(conexaoService.adicionaConexaoEstacaoLinha(conexaoDto));
        } catch (Exception e) {
            return handleErrors(e);
        }
    }

    @Operation(summary = "Exclui uma conexão pelo Id", description = "Exclui uma conexão a partir do seu ID")
    @ApiResponse(responseCode = "204", description = "Conexão excluida com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<ConexaoModel> optionalConexao = conexaoService.findById(id);
        return optionalConexao
                .map(conexao -> {
                    try {
                        conexaoService.deleteById(id);
                        return ResponseEntity.noContent().build();
                    } catch (Exception e) {
                        return handleErrors(e);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }



}

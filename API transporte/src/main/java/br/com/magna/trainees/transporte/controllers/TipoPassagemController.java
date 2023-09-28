package br.com.magna.trainees.transporte.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.trainees.transporte.models.TipoPassagemModel;
import br.com.magna.trainees.transporte.services.TipoPassagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tipo Passagem", description = "Api para gerenciamento de Tipo Passagens no sistema")
@RestController
@RequestMapping("/tipo-passagem")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoPassagemController extends GenericController {
	
	final TipoPassagemService tipoPassagemService;

	public TipoPassagemController(TipoPassagemService tipoPassagemService) {
		this.tipoPassagemService = tipoPassagemService;
	}
	
	@Operation(summary = "Lista todos os tipos de passagem", description = "Lista todos os tipos de passagem do sistema")
    @ApiResponse(responseCode = "200", description = "Tipos de passagem encontrados com sucesso",
    				content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TipoPassagemModel.class))))
    @GetMapping("/")
    public ResponseEntity<Object> get(){
        return ResponseEntity.status(HttpStatus.OK).body(tipoPassagemService.getAll());
    }
	
	@Operation(summary = "Recupera um tipo de passagem pelo ID", description = "Recupera os dados de um tipo viagem a partir do seu ID")
    @ApiResponse(responseCode = "200", description = "Tipo de passagem encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoPassagemModel.class)))
    @ApiResponse(responseCode = "404", description = "Tipo de passagem não encontrado")
    @GetMapping("/{id}")
	public ResponseEntity<TipoPassagemModel> getById(@PathVariable Long id){
			Optional<TipoPassagemModel> optional = tipoPassagemService.findById(id);
			return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	

}

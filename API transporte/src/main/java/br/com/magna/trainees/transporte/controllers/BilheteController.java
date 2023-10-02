package br.com.magna.trainees.transporte.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.services.BilheteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bilhetes", description = "Api para gerenciamento de Bilhetes no sistema")
@RestController
@RequestMapping("/bilhete")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BilheteController extends GenericController{
	
	final BilheteService bilheteService;
	
	public BilheteController(BilheteService bilheteService) {
		this.bilheteService = bilheteService;
	}
	
	@Operation(summary = "Lista todos os bilhetes", description = "Lista todos os bilhetes do sistema")
	@ApiResponse(responseCode = "200", description = "Bilhetes encontrados com sucesso",
    		content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BilheteModel.class))))
	@GetMapping("/")
	public ResponseEntity<Object> get(){
		return ResponseEntity.status(HttpStatus.OK).body(bilheteService.getAll());
	}
	
	@Operation(summary = "Recupera um bilhete por ID", description = "Recupera os dados de um bilhete a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Bilhete encontrado com sucesso",
	content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BilheteModel.class))))
	@ApiResponse(responseCode = "404", description = "Bilhete não encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<BilheteModel> getById(@PathVariable Long id){
		Optional<BilheteModel> optional = bilheteService.findById(id);
		return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@Operation(summary = "Gera um bilhete", description = "Gera um bilhete")
	@ApiResponse(responseCode = "201", description = "Bilhete gerado com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = BilheteModel.class)))
	@PostMapping("/")
	public ResponseEntity<Object> save(){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(bilheteService.gerarBilhete());
		} catch (Exception e) {
			return handleErrors(e);
		}
	}
	
	@Operation(summary = "Exclui um bilhete pelo Id", description = "Exclui um bilhete a partir do seu ID")
	@ApiResponse(responseCode = "204", description = "Bilhete excluido com sucesso")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<BilheteModel> optionalBilhete =  bilheteService.findById(id);
		return optionalBilhete
				.map(bilhete -> {
					try {
						bilheteService.deleteById(id);
						return ResponseEntity.noContent().build();
					} catch (Exception e) {
						return handleErrors(e);
					}
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@Operation(summary = "Utiliza um bilhete pelo ID", description = "Utiliza um bilhete a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Bilhete usado com sucesso")
	@PutMapping("/{id}")
	public ResponseEntity<Object> utilizarBilhete(@PathVariable Long id){
		try {
			BilheteModel bilhete = new BilheteModel();
			bilhete.setId(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(bilheteService.utilizarBilhete(bilhete));
		} catch (Exception e) {
			return handleErrors(e);
		}
	}

}

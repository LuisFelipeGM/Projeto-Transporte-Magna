package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.services.EstacaoService;
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

@Tag(name = "Estação", description = "Api para gerenciamento de Estações no sistema")
@RestController
@RequestMapping("/estacao")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstacaoController extends GenericController {

	final EstacaoService estacaoService;

	public EstacaoController(EstacaoService estacaoService) {
		this.estacaoService = estacaoService;
	}

	@Operation(summary = "Lista todas as estações", description = "Lista todas as estações do sistema")
	@ApiResponse(responseCode = "200", description = "Estações encontradas com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstacaoModel.class))))
	@GetMapping("/")
	public ResponseEntity<Object> get() {
		return ResponseEntity.status(HttpStatus.OK).body(estacaoService.getAll());
	}

	@Operation(summary = "Recupera uma estação por ID", description = "Recupera os dados de uma estação a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Estação encontrada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstacaoModel.class))))
	@ApiResponse(responseCode = "404", description = "Estação não encontrada")
	@GetMapping("/{id}")
	public ResponseEntity<EstacaoModel> getById(@PathVariable Long id) {
		Optional<EstacaoModel> optional = estacaoService.findById(id);
		return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Salva a estação", description = "Salva a estação")
	@ApiResponse(responseCode = "201", description = "Linha salva com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstacaoModel.class))))
	@PostMapping("/")
	public ResponseEntity<Object> save(@Valid @RequestBody EstacaoDto estacaoDto, BindingResult result) {
		return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
				: ResponseEntity.status(HttpStatus.CREATED).body(estacaoService.adicionaEstacao(estacaoDto));
	}

	@Operation(summary = "Exclui uma estação pelo Id", description = "Exclui uma estação a partir do seu ID")
	@ApiResponse(responseCode = "204", description = "Estação excluida com sucesso")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<EstacaoModel> optionalEstacao = estacaoService.findById(id);
		return optionalEstacao.map(estacaoModel -> {
			estacaoService.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Altera uma estação pelo Id", description = "Altera uma estação a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Estação alterada com sucesso")
	@PutMapping("/{id}")
	public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody EstacaoDto estacaoDto,
			BindingResult result) {
		return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
				: ResponseEntity.status(HttpStatus.CREATED).body(estacaoService.putEstacao(estacaoDto, id));
	}

}

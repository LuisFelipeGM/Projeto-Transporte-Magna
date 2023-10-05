package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.dtos.BairroDto;
import br.com.magna.trainees.transporte.models.BairroModel;
import br.com.magna.trainees.transporte.services.BairroService;
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

import java.util.List;
import java.util.Optional;

@Tag(name = "Bairro", description = "Api para gerenciamento de Bairros no sistema")
@RestController
@RequestMapping("/bairro")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BairroController extends GenericController {

	final BairroService bairroService;

	public BairroController(BairroService bairroService) {
		this.bairroService = bairroService;
	}

	@Operation(summary = "Lista todos os bairros", description = "Lista todos os bairros do sistema")
	@ApiResponse(responseCode = "200", description = "Bairros encontrados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BairroModel.class))))
	@GetMapping("/")
	public ResponseEntity<Object> get() {
		return ResponseEntity.status(HttpStatus.OK).body(bairroService.getAll());
	}

	@Operation(summary = "Recupera um bairro por ID", description = "Recupera os dados de um bairro a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Bairro encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BairroModel.class)))
	@ApiResponse(responseCode = "404", description = "Bairro não encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<BairroModel> getById(@PathVariable Long id) {
		Optional<BairroModel> optional = bairroService.findById(id);
		return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Recupera um bairro pelo nome", description = "Recupera os dados de um bairro a partir do seu nome")
	@ApiResponse(responseCode = "200", description = "Bairro encontrado com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BairroModel.class))))
	@ApiResponse(responseCode = "404", description = "Bairro não encontrado")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<BairroModel>> getByNome(@PathVariable String nome) {
		List<BairroModel> bairros = bairroService.findByNome(nome);
		return bairros.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(bairros);
	}

	@Operation(summary = "Salva o Bairro", description = "Salva o Bairro")
	@ApiResponse(responseCode = "201", description = "Bairro salvo com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BairroModel.class)))
	@PostMapping("/")
	public ResponseEntity<Object> save(@Valid @RequestBody BairroDto bairroDto, BindingResult result) {
		return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
				: ResponseEntity.status(HttpStatus.CREATED).body(bairroService.adicionaBairro(bairroDto));
	}

	@Operation(summary = "Exclui um bairro pelo Id", description = "Exclui um bairro a partir do seu ID")
	@ApiResponse(responseCode = "204", description = "Bairro excluido com sucesso")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<BairroModel> optionalBairro = bairroService.findById(id);
		return optionalBairro.map(bairro -> {
			bairroService.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Altera um bairro pelo Id", description = "Altera um bairro a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Bairro alterado com sucesso")
	@PutMapping("/{id}")
	public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody BairroDto bairroDto,
			BindingResult result) {
		Optional<BairroModel> optionalBairro = bairroService.findById(id);
		if (optionalBairro.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
					: ResponseEntity.status(HttpStatus.CREATED).body(bairroService.putBairro(bairroDto, id));
		}

	}

}

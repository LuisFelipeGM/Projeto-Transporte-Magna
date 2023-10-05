package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.dtos.EnderecoDto;
import br.com.magna.trainees.transporte.models.EnderecoModel;
import br.com.magna.trainees.transporte.services.EnderecoService;
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

@Tag(name = "Endereco", description = "API para o gerenciamento de enderecos no sistema")
@RestController
@RequestMapping("/endereco")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EnderecoController extends GenericController {

	final EnderecoService enderecoService;

	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@Operation(summary = "Lista todos os endereços", description = "Lista todos os endereços do sistema")
	@ApiResponse(responseCode = "200", description = "Endereços encontrados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EnderecoModel.class))))
	@GetMapping("/")
	public ResponseEntity<Object> get() {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.getAll());
	}

	@Operation(summary = "Recupera um endereço por ID", description = "Recupera os dados de um endereço a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoModel.class)))
	@ApiResponse(responseCode = "404", description = "Endereço não encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoModel> getById(@PathVariable Long id) {
		Optional<EnderecoModel> optionalEndereco = enderecoService.findById(id);
		return optionalEndereco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Salva o endereco", description = "Salva o endereco")
	@ApiResponse(responseCode = "201", description = "Endereço salvo com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoModel.class)))
	@PostMapping("/")
	public ResponseEntity<Object> save(@Valid @RequestBody EnderecoDto enderecoDto, BindingResult result) {
		try {
			return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
					: ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.adicionarEndereco(enderecoDto));
		} catch (Exception e) {
			return handleErrors(e);
		}
	}

	@Operation(summary = "Exclui um endereco pelo Id", description = "Exclui um endereco a partir do seu ID")
	@ApiResponse(responseCode = "204", description = "Endereco excluido com sucesso")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<EnderecoModel> optionalEndereco = enderecoService.findById(id);
		return optionalEndereco.map(endereco -> {
			enderecoService.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Altera um endereco pelo Id", description = "Altera um endereco a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Endereco alterado com sucesso")
	@PutMapping("/{id}")
	public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody EnderecoDto enderecoDto,
			BindingResult result) {
		Optional<EnderecoModel> optionalEndereco = enderecoService.findById(id);
		if (optionalEndereco.isEmpty())
			return ResponseEntity.notFound().build();
		try {
			return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
					: ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.putEndereco(enderecoDto, id));
		} catch (Exception e) {
			return handleErrors(e);
		}

	}

}

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.trainees.transporte.dtos.CartaoDto;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.services.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Cartao", description = "Api para gerenciamento de Cartões no sistema")
@RestController
@RequestMapping("/cartao")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartaoController extends GenericController {

	final CartaoService cartaoService;

	public CartaoController(CartaoService cartaoService) {
		this.cartaoService = cartaoService;
	}

	@Operation(summary = "Lista todos os cartões", description = "Lista todos os cartões do sistema")
	@ApiResponse(responseCode = "200", description = "Cartões encontrados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CartaoModel.class))))
	@GetMapping("/")
	public ResponseEntity<Object> get() {
		return ResponseEntity.status(HttpStatus.OK).body(cartaoService.getAll());
	}

	@Operation(summary = "Recupera um cartão por ID", description = "Recupera os dados de um cartão a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Cartão encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoModel.class)))
	@ApiResponse(responseCode = "404", description = "Cartão não encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<CartaoModel> geyById(@PathVariable Long id) {
		Optional<CartaoModel> optional = cartaoService.findById(id);
		return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Salva o Cartão", description = "Salva o Cartão")
	@ApiResponse(responseCode = "201", description = "Cartão salvo com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoModel.class)))
	@PostMapping("/")
	public ResponseEntity<Object> save(@Valid @RequestBody CartaoDto cartaoDto, BindingResult result) {
		return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
				: ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.adicionaCartao(cartaoDto));
	}

	@Operation(summary = "Exclui um cartão pelo Id", description = "Exclui um cartão a partir do seu ID")
	@ApiResponse(responseCode = "204", description = "Cartão excluido com sucesso")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<CartaoModel> optionalCartao = cartaoService.findById(id);
		return optionalCartao.map(passageiro -> {
			cartaoService.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Altera um Cartão pelo Id", description = "Altera um cartão a partir do seu ID")
	@ApiResponse(responseCode = "200", description = "Cartão alterado com sucesso")
	@PutMapping("/{id}")
	public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody CartaoDto cartaoDto,
			BindingResult result) {
		Optional<CartaoModel> optionalCartao = cartaoService.findById(id);
		if (optionalCartao.isEmpty())
			return ResponseEntity.notFound().build();
		try {
			return result.hasErrors() ? ResponseEntity.unprocessableEntity().body(getErrors(result))
					: ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.putCartao(cartaoDto, id));
		} catch (Exception e) {
			return handleErrors(e);
		}
	}

}

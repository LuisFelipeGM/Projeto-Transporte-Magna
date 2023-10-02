package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ViagemDto(
		
		Long idBilhete,
		
		Long idCartao,
		
		@Schema(example = "3")
        @NotNull(message = "O Id da Estação é obrigatório")
		Long idEstacao
		
		) {

}

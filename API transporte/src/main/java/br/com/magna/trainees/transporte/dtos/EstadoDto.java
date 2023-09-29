package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EstadoDto(
        @Schema(example = "São Paulo")
        @NotBlank(message = "O Nome do estado é obrigatório")
        String nome,

        @Schema(example = "SP")
        @NotBlank(message = "A Sigla do Estado é obrigatório")
        String sigla
) {
}

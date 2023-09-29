package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BairroDto(

        @Schema(example = "Jardim Helena")
        @NotBlank(message = "O nome do bairro é obrigatório")
        String nome,

        @Schema(example = "1")
        @NotNull(message = "O Id da cidade é obrigatório")
        Long idCidade

) {
}

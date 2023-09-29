package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ConexaoDto(

        @Schema(example = "2")
        @NotNull(message = "O Id da Linha é obrigatório")
        Long idLinha,

        @Schema(example = "3")
        @NotNull(message = "O Id da Estação é obrigatório")
        Long idEstacao

) {
}

package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LinhaDto (

        @Schema(example = "Esmeralda")
        @NotBlank(message = "O nome da Linha é obrigatório")
        String nome,

        @Schema(example = "9")
        @NotNull(message = "O numero da Linha é obrigatório")
        int numero

) {

}
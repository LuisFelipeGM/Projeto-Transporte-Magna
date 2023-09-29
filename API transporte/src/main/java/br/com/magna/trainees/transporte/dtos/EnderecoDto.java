package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDto(

        @Schema(example = "Rua Fidêncio Ramos")
        @NotBlank(message = "O Logradouro é obrigatório")
        String logradouro,

        @Schema(example = "1222")
        @NotNull(message = "O Numero é obrigatório")
        Integer numero,

        @Schema(example = "2 entrada do Shopping")
        String complementoNumero,

        @Schema(example = "3")
        @NotNull(message = "O Id da Estação é obrigatório")
        Long idEstacao,

        @Schema(example = "3")
        @NotNull(message = "O Id do Bairro é obrigatório")
        Long idBairro

) {
}

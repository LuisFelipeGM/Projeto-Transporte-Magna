package br.com.magna.trainees.transporte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.sql.Time;

public record EstacaoDto(

        @Schema(example = "Pinheiros")
        @NotBlank(message = "O nome da Estação é obrigatório")
        String nome,

        Time horarioAbertura,

        Time horarioFechamento

) {
}

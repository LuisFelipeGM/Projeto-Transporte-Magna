package br.com.magna.trainees.transporte.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassageiroDto {

    @Schema(example = "Luís Felipe Garcia Menezes")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(example = "12345678901")
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    @Schema(example = "2003-09-01")
    private LocalDate dataNascimento;
    
}

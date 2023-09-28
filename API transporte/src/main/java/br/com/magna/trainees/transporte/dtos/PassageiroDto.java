package br.com.magna.trainees.transporte.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PassageiroDto (
		
		@Schema(example = "Luís Felipe Garcia Menezes")
	    @NotBlank(message = "O nome é obrigatório")
	    String nome,
	    
	    @Schema(example = "12345678901")
	    @NotNull(message = "CPF é obrigatório")
	    String cpf,
	    
	    @Schema(example = "2003-09-01")
	    LocalDate dataNascimento
	    
		) {    
}

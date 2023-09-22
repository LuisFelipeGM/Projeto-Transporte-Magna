package br.com.magna.trainees.transporte.dtos;

import br.com.magna.trainees.transporte.enums.TipoPassageiro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartaoDto {

    @Schema(example = "627384938273")
    @NotNull(message = "O Número do cartão é obrigatório")
    private Long numero;

    private TipoPassageiro tipoPassageiro;

    @Schema(example = "1")
    @NotNull(message = "O Id do Passageiro é obrigatório")
    private Long idPassageiro;
    
}

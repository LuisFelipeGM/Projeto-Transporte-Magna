package br.com.magna.trainees.transporte.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroDto {
    private String mensagem;
    private String detalhes;
    
}

package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContratoDTO {
    private String numero;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long idAluno;
    private String nomeAluno;
}

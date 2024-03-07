package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoDTO {
    private String modelo;
    private String placa;
    private Long idInstrutor;
    private Long idCategoria;
    private String nomeInstrutor;
    private String tipoCategoria;
}

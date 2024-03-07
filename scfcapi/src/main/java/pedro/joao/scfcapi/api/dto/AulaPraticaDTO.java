package pedro.joao.scfcapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AulaPraticaDTO {
    private LocalDate dataAulaPratica;
    private LocalTime horarioAulaPratica;
    private Long idInstrutor;
    private String nomeInstrutor;
    private String idCategoria;
    private String tipoCategoria;
    private String idVeiculo;
    private String marcaVeiculo;
}

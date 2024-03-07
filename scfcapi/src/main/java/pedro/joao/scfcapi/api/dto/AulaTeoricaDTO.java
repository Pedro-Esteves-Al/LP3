package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaTeoricaDTO {
    private LocalDate dataAulaTeorica;
    private LocalTime horarioAulaTeorica;
    private Long idInstrutor;
    private String nomeInstrutor;
}

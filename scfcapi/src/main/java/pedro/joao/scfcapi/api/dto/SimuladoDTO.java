package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimuladoDTO {
    private LocalDate dataSimulado;
    private LocalTime horarioSimulado;
}

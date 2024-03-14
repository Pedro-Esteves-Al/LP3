package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Simulado;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimuladoDTO {
    private LocalDate dataSimulado;
    private LocalTime horarioSimulado;

    public static SimuladoDTO create(Simulado simulado) {
        ModelMapper modelMapper = new ModelMapper();
        SimuladoDTO dto = modelMapper.map(simulado, SimuladoDTO.class);
        return dto;
    }
}

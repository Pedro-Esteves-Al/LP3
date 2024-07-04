package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.entity.Instrutor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaTeoricaDTO {
    private Long id;
    private LocalDate dataAulaTeorica;
    private LocalTime horarioAulaTeorica;
    private Long idInstrutor;
    private String nomeInstrutor;

    public static AulaTeoricaDTO create(AulaTeorica aulaTeorica) {
        ModelMapper modelMapper = new ModelMapper();
        AulaTeoricaDTO dto = modelMapper.map(aulaTeorica, AulaTeoricaDTO.class);
        dto.nomeInstrutor = aulaTeorica.getInstrutor().getNome();
        return dto;
    }
}

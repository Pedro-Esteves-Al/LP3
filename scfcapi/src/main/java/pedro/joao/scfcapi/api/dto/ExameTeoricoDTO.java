package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.ExameTeorico;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExameTeoricoDTO {
    private Long id;
    private LocalDate dataExameTeorico;
    private LocalTime horarioExameTeorico;
    private String localExameTeorico;
    /*private Long idCategoria;
    private String tipoCategoria;*/

    public static ExameTeoricoDTO create(ExameTeorico exameTeorico) {
        ModelMapper modelMapper = new ModelMapper();
        ExameTeoricoDTO dto = modelMapper.map(exameTeorico, ExameTeoricoDTO.class);
       // dto.tipoCategoria = exameTeorico.getCategoria().getTipo();
        return dto;
    }
}

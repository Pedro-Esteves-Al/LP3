package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.ExamePratico;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamePraticoDTO {
    private Long id;
    private LocalDate dataExamePratico;
    private LocalTime horarioExamePratico;
    private String local;
    private Long idCategoria;
    private String tipoCategoria;

    public static ExamePraticoDTO create(ExamePratico examePratico) {
        ModelMapper modelMapper = new ModelMapper();
        ExamePraticoDTO dto = modelMapper.map(examePratico, ExamePraticoDTO.class);
        dto.tipoCategoria = examePratico.getCategoria().getTipo();
        return dto;
    }
}

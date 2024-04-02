package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.AlunoExamePratico;
import pedro.joao.scfcapi.model.entity.InstrutorExamePratico;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstrutorExamePraticoDTO {
    private Long idInstrutor;
    private Long idExamePratico;
    private LocalDate dataExamePratico;
    private LocalTime horarioExamePratico;
    private String nomeInstrutor;
    private String localExamePratico;

    public static InstrutorExamePraticoDTO create(InstrutorExamePratico instrutorExamePratico) {
        ModelMapper modelMapper = new ModelMapper();
        InstrutorExamePraticoDTO dto = modelMapper.map(instrutorExamePratico, InstrutorExamePraticoDTO.class);
        dto.localExamePratico = instrutorExamePratico.getExamePratico().getLocal();
        dto.nomeInstrutor = instrutorExamePratico.getInstrutor().getNome();
        dto.dataExamePratico = instrutorExamePratico.getExamePratico().getDataExamePratico();
        dto.horarioExamePratico = instrutorExamePratico.getExamePratico().getHorarioExamePratico();
        return dto;
    }
}

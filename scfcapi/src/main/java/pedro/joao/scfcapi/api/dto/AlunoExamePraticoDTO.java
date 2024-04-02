package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.AlunoExamePratico;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoExamePraticoDTO {
    private Long idAluno;
    private Long idExamePratico;
    private LocalDate dataExamePratico;
    private LocalTime horarioExamePratico;
    private String nomeAluno;
    private String localExamePratico;

    public static AlunoExamePraticoDTO create(AlunoExamePratico alunoExamePratico) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoExamePraticoDTO dto = modelMapper.map(alunoExamePratico, AlunoExamePraticoDTO.class);
        dto.localExamePratico = alunoExamePratico.getExamePratico().getLocal();
        dto.nomeAluno = alunoExamePratico.getAluno().getNome();
        dto.dataExamePratico = alunoExamePratico.getExamePratico().getDataExamePratico();
        dto.horarioExamePratico = alunoExamePratico.getExamePratico().getHorarioExamePratico();
        return dto;
    }
}

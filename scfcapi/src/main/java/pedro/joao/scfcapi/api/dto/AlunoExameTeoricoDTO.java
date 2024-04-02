package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.AlunoExameTeorico;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoExameTeoricoDTO {
    private Long idAluno;
    private Long idExameTeorico;
    private LocalDate dataExameTeorico;
    private LocalTime horarioExameTeorico;
    private String localExameTeorico;
    private String tipoCategoria;
    private String nomeAluno;

    public static AlunoExameTeoricoDTO create(AlunoExameTeorico alunoExameTeorico) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoExameTeoricoDTO dto = modelMapper.map(alunoExameTeorico, AlunoExameTeoricoDTO.class);
        dto.localExameTeorico = alunoExameTeorico.getExameTeorico().getLocalExameTeorico();
        dto.nomeAluno = alunoExameTeorico.getAluno().getNome();
        dto.dataExameTeorico = alunoExameTeorico.getExameTeorico().getDataExameTeorico();
        dto.horarioExameTeorico = alunoExameTeorico.getExameTeorico().getHorarioExameTeorico();
        dto.tipoCategoria = alunoExameTeorico.getExameTeorico().getCategoria().getTipo();
        return dto;
    }
}

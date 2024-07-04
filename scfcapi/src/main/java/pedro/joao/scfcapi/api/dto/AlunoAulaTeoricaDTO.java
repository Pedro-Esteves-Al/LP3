package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoAulaTeoricaDTO {
    private Long id;
    private Long idAluno;
    private Long idAulaTeorica;
    private String nomeAluno;
    private String nomeInstrutor;
    private LocalDate dataAulaTeorica;
    private LocalTime horarioAulaTeorica;

    public static AlunoAulaTeoricaDTO create(AlunoAulaTeorica alunoAulaTeorica) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoAulaTeoricaDTO dto = modelMapper.map(alunoAulaTeorica, AlunoAulaTeoricaDTO.class);
        dto.nomeInstrutor = alunoAulaTeorica.getAulaTeorica().getInstrutor().getNome();
        dto.nomeAluno = alunoAulaTeorica.getAluno().getNome();
        dto.dataAulaTeorica = alunoAulaTeorica.getAulaTeorica().getDataAulaTeorica();
        dto.horarioAulaTeorica = alunoAulaTeorica.getAulaTeorica().getHorarioAulaTeorica();
        return dto;
    }
}

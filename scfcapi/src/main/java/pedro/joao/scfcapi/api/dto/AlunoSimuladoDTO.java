package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pedro.joao.scfcapi.model.entity.AlunoSimulado;

import java.time.LocalDate;
import java.time.LocalTime;

import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoSimuladoDTO {
    private Long idAluno;
    private Long idSimulado;
    private String nomeAluno;
    private LocalDate dataSimulado;
    private LocalTime horarioSimulado;

    public static AlunoSimuladoDTO create(AlunoSimulado alunoSimulado) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoSimuladoDTO dto = modelMapper.map(alunoSimulado, AlunoSimuladoDTO.class);
        dto.nomeAluno = alunoSimulado.getAluno().getNome();
        dto.dataSimulado = alunoSimulado.getSimulado().getDataSimulado();
        dto.horarioSimulado = alunoSimulado.getSimulado().getHorarioSimulado();
        return dto;
    }
}

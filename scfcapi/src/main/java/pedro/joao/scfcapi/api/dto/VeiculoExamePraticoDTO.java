package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import org.modelmapper.ModelMapper;

import pedro.joao.scfcapi.model.entity.VeiculoExamePratico;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoExamePraticoDTO {
    private Long id;
    private Long idVeiculo;
    private Long idExamePratico;
    private LocalDate dataExamePratico;
    private LocalTime horarioExamePratico;
    private String local;
    private String modeloVeiculo;
    private String placa;
    private Long idInstrutor;
    private String nomeInstrutor;
    private Long idCategoria;
    private String tipoCategoria;

    public static VeiculoExamePraticoDTO create(VeiculoExamePratico veiculoExamePratico) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoExamePraticoDTO dto = modelMapper.map(veiculoExamePratico, VeiculoExamePraticoDTO.class);
        dto.local = veiculoExamePratico.getExamePratico().getLocal();
        dto.modeloVeiculo = veiculoExamePratico.getVeiculo().getModelo();
        dto.dataExamePratico = veiculoExamePratico.getExamePratico().getDataExamePratico();
        dto.horarioExamePratico = veiculoExamePratico.getExamePratico().getHorarioExamePratico();
        dto.idInstrutor = veiculoExamePratico.getVeiculo().getInstrutor().getId();
        dto.nomeInstrutor = veiculoExamePratico.getVeiculo().getInstrutor().getNome();
        dto.idCategoria = veiculoExamePratico.getVeiculo().getCategoria().getId();
        dto.tipoCategoria = veiculoExamePratico.getVeiculo().getCategoria().getTipo();
        return dto;
    }
}

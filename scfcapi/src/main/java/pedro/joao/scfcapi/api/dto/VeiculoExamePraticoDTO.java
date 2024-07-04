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
    private String modelo;
    private String placa;

    public static VeiculoExamePraticoDTO create(VeiculoExamePratico veiculoExamePratico) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoExamePraticoDTO dto = modelMapper.map(veiculoExamePratico, VeiculoExamePraticoDTO.class);
        dto.local = veiculoExamePratico.getExamePratico().getLocal();
        dto.modelo = veiculoExamePratico.getVeiculo().getModelo();
        dto.dataExamePratico = veiculoExamePratico.getExamePratico().getDataExamePratico();
        dto.horarioExamePratico = veiculoExamePratico.getExamePratico().getHorarioExamePratico();
        return dto;
    }
}

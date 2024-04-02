package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Veiculo;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoDTO {
    private String modelo;
    private String placa;
    private Long idInstrutor;
    private Long idCategoria;
    private String nomeInstrutor;
    private String tipoCategoria;

    public static VeiculoDTO create(Veiculo veiculo) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);
        dto.nomeInstrutor = veiculo.getInstrutor().getNome();
        dto.tipoCategoria = veiculo.getCategoria().getTipo();
        return dto;
    }
}

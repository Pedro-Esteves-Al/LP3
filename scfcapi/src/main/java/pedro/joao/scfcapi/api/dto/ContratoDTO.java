package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Contrato;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContratoDTO {
    private Long id;
    private String numero;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long idAluno;
    private String nomeAluno;
    private Long idCategoria;
    private String tipoCategoria;

    public static ContratoDTO create(Contrato contrato) {
        ModelMapper modelMapper = new ModelMapper();
        ContratoDTO dto = modelMapper.map(contrato, ContratoDTO.class);
        dto.nomeAluno = contrato.getAluno().getNome();
        dto.tipoCategoria = contrato.getCategoria().getTipo();
        return dto;
    }
}

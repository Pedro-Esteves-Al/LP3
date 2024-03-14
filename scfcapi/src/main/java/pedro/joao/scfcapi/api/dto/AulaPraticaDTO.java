package pedro.joao.scfcapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.AulaPratica;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AulaPraticaDTO {
    private LocalDate dataAulaPratica;
    private LocalTime horarioAulaPratica;
    private Long idInstrutor;
    private String nomeInstrutor;
    private String idCategoria;
    private String tipoCategoria;
    private String idVeiculo;
    private String marcaVeiculo;

    public static AulaPraticaDTO create(AulaPratica aulaPratica) {
        ModelMapper modelMapper = new ModelMapper();
        AulaPraticaDTO dto = modelMapper.map(aulaPratica, AulaPraticaDTO.class);
        dto.nomeInstrutor = aulaPratica.getInstrutor().getNome();
        dto.tipoCategoria = aulaPratica.getCategoria().getTipo();
        dto.marcaVeiculo = aulaPratica.getVeiculo().getModelo();
        return dto;
    }
}

package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Instrutor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InstrutorDTO {
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String cnh;
    private String clt;

    public static InstrutorDTO create(Instrutor instrutor) {
        ModelMapper modelMapper = new ModelMapper();
        InstrutorDTO dto = modelMapper.map(instrutor, InstrutorDTO.class);
        return dto;
    }
}

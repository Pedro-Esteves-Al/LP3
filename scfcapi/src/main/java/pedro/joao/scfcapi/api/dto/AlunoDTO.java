package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Aluno;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AlunoDTO {
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
    /*private boolean aprovadoExameTeorico;
    private boolean aprovadoExamePratico;
    private Long exame_pratico_id;
    private Long exame_teorico_id;*/



    public static AlunoDTO create(Aluno aluno) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoDTO dto = modelMapper.map(aluno, AlunoDTO.class);
        return dto;
    }
}
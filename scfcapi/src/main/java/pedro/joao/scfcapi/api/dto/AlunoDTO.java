package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean aprovadoExameTeorico;
    private boolean aprovadoExamePratico;
    private Long idContrato;
    private String numeroContrato;
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
}

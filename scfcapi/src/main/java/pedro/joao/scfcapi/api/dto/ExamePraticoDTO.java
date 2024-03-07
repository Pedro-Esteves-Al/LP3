package pedro.joao.scfcapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamePraticoDTO {
    private LocalDate dataExamePratico;
    private LocalTime horarioExamePratico;
    private String local;

    //lista de alunos, instrutores e veiculos??
}

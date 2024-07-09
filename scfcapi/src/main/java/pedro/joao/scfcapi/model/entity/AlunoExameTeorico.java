package pedro.joao.scfcapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoExameTeorico {

    private boolean aprovado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private ExameTeorico exameTeorico;
}

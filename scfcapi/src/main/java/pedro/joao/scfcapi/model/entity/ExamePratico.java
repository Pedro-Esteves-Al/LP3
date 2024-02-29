package pedro.joao.scfcapi.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamePratico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private LocalDate dataExamePratico;
    private LocalTime horarioExamePratico;
    private String local;
   /* @ManyToOne
    private Instrutor instrutor;
    @ManyToOne
    private Veiculo veiculo;
    @ManyToOne
    private Categoria categoria;*/
}
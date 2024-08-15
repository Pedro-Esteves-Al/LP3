package pedro.joao.scfcapi.model.entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AulaPratica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private LocalDate dataAulaPratica;
    private LocalTime horarioAulaPratica;

    @ManyToOne
    private Instrutor instrutor;
    @ManyToOne
    private Veiculo veiculo;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Aluno aluno;
}

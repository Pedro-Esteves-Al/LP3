package pedro.joao.scfcapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String tipo;

    @ManyToOne
    private Veiculo veiculo;
    @ManyToOne
    private ExamePratico examePratico;
    @ManyToOne
    private ExameTeorico exameTeorico;
}

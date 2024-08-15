package pedro.joao.scfcapi.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String numero;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    
    @ManyToOne
    private Aluno aluno;
    @ManyToOne
    private Categoria categoria;
}

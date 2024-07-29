package pedro.joao.scfcapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

//@EqualsAndHashCode(callSuper = true)
//@SuperBuilder

public class Aluno extends Pessoa {
    @ManyToOne
    private Contrato contrato;
}

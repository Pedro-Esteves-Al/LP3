package pedro.joao.scfcapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
    private String matricula;
}

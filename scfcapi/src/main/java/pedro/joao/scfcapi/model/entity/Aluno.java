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
    private boolean matricula;//isso aqui não pode existir, mas se comentar não roda
    //private boolean aprovadoExamePratico; //atributo da ligação, já excluido no banco de dados
}

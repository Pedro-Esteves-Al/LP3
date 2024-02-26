package pedro.joao.scfcapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Veiculo {
    private String modelo;
    private String placa;

    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Instrutor instrutor;
}

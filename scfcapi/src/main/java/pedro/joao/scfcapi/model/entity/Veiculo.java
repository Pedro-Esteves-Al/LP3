package pedro.joao.scfcapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Veiculo {
    @ Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String modelo;
    private String placa;

   @ManyToOne
   private Instrutor instrutor;
    @ManyToOne
    private Categoria categoria;
}

package pedro.joao.scfcapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AulaTeorica {
    private LocalDate dataAulaTeorica;
    private LocalTime horarioAulaTeorica;

    @ManyToOne
    private Instrutor instrutor;
}

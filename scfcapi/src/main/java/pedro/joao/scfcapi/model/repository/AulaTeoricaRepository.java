package pedro.joao.scfcapi.model.repository;

import pedro.joao.scfcapi.model.entity.AulaTeorica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AulaTeoricaRepository extends JpaRepository<AulaTeorica, Long> {

}

package pedro.joao.scfcapi.model.repository;

import pedro.joao.scfcapi.model.entity.AulaPratica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AulaPraticaRepository extends JpaRepository<AulaPratica, Long> {

}
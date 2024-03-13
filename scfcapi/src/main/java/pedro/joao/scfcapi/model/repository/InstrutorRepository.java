package pedro.joao.scfcapi.model.repository;

import pedro.joao.scfcapi.model.entity.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

}

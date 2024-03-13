package pedro.joao.scfcapi.model.repository;

import pedro.joao.scfcapi.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}

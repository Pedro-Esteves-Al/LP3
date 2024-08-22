package pedro.joao.scfcapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

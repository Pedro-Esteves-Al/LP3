package pedro.joao.scfcapi.model.repository;

import com.example.scfcapi.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optimal;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{

}
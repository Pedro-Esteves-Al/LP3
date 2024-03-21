package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.repository.AlunoRepository;
import pedro.joao.scfcapi.model.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    private AlunoRepository repository;
    public AlunoService(AlunoRepository repository) { this.repository = repository;}
    public List<Aluno> getAlunos() {return repository.findAll(); }
    public Optional<Aluno> getAlunoById(Long id) {return repository.findById(id);}

}

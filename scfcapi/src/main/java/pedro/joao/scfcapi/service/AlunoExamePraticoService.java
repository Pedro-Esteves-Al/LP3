package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AlunoExamePratico;
import pedro.joao.scfcapi.model.repository.AlunoExamePraticoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoExamePraticoService {
    private AlunoExamePraticoRepository repository;
    public AlunoExamePraticoService(AlunoExamePraticoRepository repository) { this.repository = repository;}
    public List<AlunoExamePratico> getAlunoExamePratico() {return repository.findAll(); }
    public Optional<AlunoExamePratico> getAlunoExamePraticoById(Long id) {return repository.findById(id);}

    @Transactional
    public AlunoExamePratico salvar(AlunoExamePratico alunoExamePratico) {
        validar(alunoExamePratico);
        return repository.save(alunoExamePratico);
    }

    public void validar(AlunoExamePratico alunoExamePratico) {
        if (alunoExamePratico.getAluno() == null) {
            throw new RegraNegocioException("Aluno Inválido");
        }
        if (alunoExamePratico.getExamePratico() == null) {
            throw new RegraNegocioException("Exame Prático Inválida");
        }
    }
}

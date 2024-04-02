package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;

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
}

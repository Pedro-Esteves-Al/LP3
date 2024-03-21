package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.repository.ExamePraticoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExamePraticoService {
    private ExamePraticoRepository repository;
    public ExamePraticoService(ExamePraticoRepository repository) { this.repository = repository;}
    public List<ExamePratico> getExamePraticos() {return repository.findAll(); }
    public Optional<ExamePratico> getExamePraticoById(Long id) {return repository.findById(id);}
}

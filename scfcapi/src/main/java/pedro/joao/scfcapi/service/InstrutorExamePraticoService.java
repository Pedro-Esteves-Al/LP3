package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;

import pedro.joao.scfcapi.model.entity.InstrutorExamePratico;
import pedro.joao.scfcapi.model.repository.InstrutorExamePraticoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstrutorExamePraticoService {
    private InstrutorExamePraticoRepository repository;
    public InstrutorExamePraticoService(InstrutorExamePraticoRepository repository) { this.repository = repository;}
    public List<InstrutorExamePratico> getInstrutorExamePratico() {return repository.findAll(); }
    public Optional<InstrutorExamePratico> getInstrutorExamePraticoById(Long id) {return repository.findById(id);}
}

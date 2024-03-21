package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.ExameTeorico;
import pedro.joao.scfcapi.model.repository.ExameTeoricoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExameTeoricoService {
    private ExameTeoricoRepository repository;
    public ExameTeoricoService(ExameTeoricoRepository repository) { this.repository = repository;}
    public List<ExameTeorico> getExameTeoricos() {return repository.findAll(); }
    public Optional<ExameTeorico> getExameTeoricoById(Long id) {return repository.findById(id);}
}

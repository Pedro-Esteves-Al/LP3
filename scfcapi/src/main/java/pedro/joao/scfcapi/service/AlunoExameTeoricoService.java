package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;

import pedro.joao.scfcapi.model.entity.AlunoExameTeorico;
import pedro.joao.scfcapi.model.repository.AlunoExameTeoricoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoExameTeoricoService {
    private AlunoExameTeoricoRepository repository;
    public AlunoExameTeoricoService(AlunoExameTeoricoRepository repository) { this.repository = repository;}
    public List<AlunoExameTeorico> getAlunoExameTeoricos() {return repository.findAll(); }
    public Optional<AlunoExameTeorico> getAlunoExameTeoricosById(Long id) {return repository.findById(id);}
}

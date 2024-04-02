package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.repository.AlunoAulaTeoricaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoAulaTeoricaService {
    private AlunoAulaTeoricaRepository repository;
    public AlunoAulaTeoricaService(AlunoAulaTeoricaRepository repository) { this.repository = repository;}
    public List<AlunoAulaTeorica> getAlunoAulaTeorica() {return repository.findAll(); }
    public Optional<AlunoAulaTeorica> getAlunoAulaTeoricaById(Long id) {return repository.findById(id);}
}

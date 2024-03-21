package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.repository.AulaTeoricaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AulaTeoricaService {
    private AulaTeoricaRepository repository;
    public AulaTeoricaService(AulaTeoricaRepository repository) { this.repository = repository;}
    public List<AulaTeorica> getAulaTeoricas() {return repository.findAll(); }
    public Optional<AulaTeorica> getAulaTeoricaById(Long id) {return repository.findById(id);}
}

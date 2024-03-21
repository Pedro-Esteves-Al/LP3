package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.AulaPratica;
import pedro.joao.scfcapi.model.repository.AulaPraticaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AulaPraticaService {
    private AulaPraticaRepository repository;
    public AulaPraticaService(AulaPraticaRepository repository) { this.repository = repository;}
    public List<AulaPratica> getAulaPraticas() {return repository.findAll(); }
    public Optional<AulaPratica> getAulaPraticaById(Long id) {return repository.findById(id);}
}

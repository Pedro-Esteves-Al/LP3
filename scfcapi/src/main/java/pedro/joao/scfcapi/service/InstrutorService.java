package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.repository.InstrutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstrutorService {
    private InstrutorRepository repository;
    public InstrutorService(InstrutorRepository repository) { this.repository = repository;}
    public List<Instrutor> getInstrutors() {return repository.findAll(); }
    public Optional<Instrutor> getInstrutorById(Long id) {return repository.findById(id);}
}

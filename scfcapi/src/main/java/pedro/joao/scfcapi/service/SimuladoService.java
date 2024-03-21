package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.Simulado;
import pedro.joao.scfcapi.model.repository.SimuladoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimuladoService {
    private SimuladoRepository repository;
    public SimuladoService(SimuladoRepository repository) { this.repository = repository;}
    public List<Simulado> getSimulados() {return repository.findAll(); }
    public Optional<Simulado> getSimuladoById(Long id) {return repository.findById(id);}
}

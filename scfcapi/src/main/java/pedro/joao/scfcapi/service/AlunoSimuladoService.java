package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.AlunoSimulado;
import pedro.joao.scfcapi.model.repository.AlunoSimuladoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoSimuladoService {
    private AlunoSimuladoRepository repository;
    public AlunoSimuladoService(AlunoSimuladoRepository repository) { this.repository = repository;}
    public List<AlunoSimulado> getAlunoSimulado() {return repository.findAll(); }
    public Optional<AlunoSimulado> getAlunoSimuladoById(Long id) {return repository.findById(id);}
}

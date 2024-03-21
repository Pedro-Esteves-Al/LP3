package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.model.repository.VeiculoRepository;

import java.util.List;
import java.util.Optional;

@Service

public class VeiculoService {
    private VeiculoRepository repository;
    public VeiculoService(VeiculoRepository repository) { this.repository = repository;}
    public List<Veiculo> getVeiculos() {return repository.findAll(); }
    public Optional<Veiculo> getVeiculoById(Long id) {return repository.findById(id);}
}

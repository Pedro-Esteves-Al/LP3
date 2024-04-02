package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;

import pedro.joao.scfcapi.model.entity.VeiculoExamePratico;
import pedro.joao.scfcapi.model.repository.VeiculoExamePraticoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoExamePraticoService {
    private VeiculoExamePraticoRepository repository;
    public VeiculoExamePraticoService(VeiculoExamePraticoRepository repository) { this.repository = repository;}
    public List<VeiculoExamePratico> getVeiculoExamePratico() {return repository.findAll(); }
    public Optional<VeiculoExamePratico> getVeiculoExamePraticoById(Long id) {return repository.findById(id);}
}

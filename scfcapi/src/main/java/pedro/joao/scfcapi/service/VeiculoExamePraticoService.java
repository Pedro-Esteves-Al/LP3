package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
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

    @Transactional
    public VeiculoExamePratico salvar(VeiculoExamePratico veiculoExamePratico) {
        validar(veiculoExamePratico);
        return repository.save(veiculoExamePratico);
    }

    public void validar(VeiculoExamePratico veiculoExamePratico) {
        if (veiculoExamePratico.getExamePratico() == null) {
            throw new RegraNegocioException("Exame Prático Inválido");
        }
        if (veiculoExamePratico.getVeiculo() == null) {
            throw new RegraNegocioException("Veículo Inválido");
        }
    }
}

package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.model.repository.VeiculoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class VeiculoService {
    private VeiculoRepository repository;
    public VeiculoService(VeiculoRepository repository) { this.repository = repository;}
    public List<Veiculo> getVeiculos() {return repository.findAll(); }
    public Optional<Veiculo> getVeiculoById(Long id) {return repository.findById(id);}

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        validar(veiculo);
        return repository.save(veiculo);
    }

     @Transactional
    public void excluir(Veiculo veiculo) {
        Objects.requireNonNull(veiculo.getId());
        repository.delete(veiculo);
    }

    public void validar(Veiculo veiculo) {
        if (veiculo.getInstrutor() == null) {
            throw new RegraNegocioException("Instrutor Inválido");
        }
        if (veiculo.getCategoria() == null) {
            throw new RegraNegocioException("Categoria Inválida");
        }
        if (veiculo.getModelo() == null || veiculo.getModelo().trim().equals("")) {
            throw new RegraNegocioException("Modelo Inválido");
        }
        if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().equals("")) {
            throw new RegraNegocioException("Placa Inválida");
        }
    }
}

package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Simulado;
import pedro.joao.scfcapi.model.repository.SimuladoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SimuladoService {
    private SimuladoRepository repository;
    public SimuladoService(SimuladoRepository repository) { this.repository = repository;}
    public List<Simulado> getSimulados() {return repository.findAll(); }
    public Optional<Simulado> getSimuladoById(Long id) {return repository.findById(id);}

    @Transactional
    public Simulado salvar(Simulado simulado) {
        validar(simulado);
        return repository.save(simulado);
    }

     @Transactional
    public void excluir(Simulado simulado) {
        Objects.requireNonNull(simulado.getId());
        repository.delete(simulado);
    }

    public void validar(Simulado simulado) {
        if (simulado.getDataSimulado() == null) {
            throw new RegraNegocioException("Data Inválida");
        }
        if (simulado.getHorarioSimulado() == null) {
            throw new RegraNegocioException("Horário Inválido");
        }
    }
}

package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.repository.AulaTeoricaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AulaTeoricaService {
    private AulaTeoricaRepository repository;
    public AulaTeoricaService(AulaTeoricaRepository repository) { this.repository = repository;}
    public List<AulaTeorica> getAulaTeoricas() {return repository.findAll(); }
    public Optional<AulaTeorica> getAulaTeoricaById(Long id) {return repository.findById(id);}

    @Transactional
    public AulaTeorica salvar(AulaTeorica aulaTeorica) {
        validar(aulaTeorica);
        return repository.save(aulaTeorica);
    }

     @Transactional
    public void excluir(AulaTeorica aulaTeorica) {
        Objects.requireNonNull(aulaTeorica.getId());
        repository.delete(aulaTeorica);
    }

    public void validar(AulaTeorica aulaTeorica) {
        if (aulaTeorica.getInstrutor() == null) {
            throw new RegraNegocioException("Instrutor Inválido");
        }
        if (aulaTeorica.getDataAulaTeorica() == null) {
            throw new RegraNegocioException("Data Inválida");
        }
        if (aulaTeorica.getHorarioAulaTeorica() == null) {
            throw new RegraNegocioException("Horário Inválido");
        }
    }
}

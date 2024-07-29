package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaPratica;
import pedro.joao.scfcapi.model.repository.AulaPraticaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AulaPraticaService {
    private AulaPraticaRepository repository;
    public AulaPraticaService(AulaPraticaRepository repository) { this.repository = repository;}
    public List<AulaPratica> getAulaPraticas() {return repository.findAll(); }
    public Optional<AulaPratica> getAulaPraticaById(Long id) {return repository.findById(id);}

    @Transactional
    public AulaPratica salvar(AulaPratica aulaPratica) {
        validar(aulaPratica);
        return repository.save(aulaPratica);
    }

     @Transactional
    public void excluir(AulaPratica aulaPratica) {
        Objects.requireNonNull(aulaPratica.getId());
        repository.delete(aulaPratica);
    }

    public void validar(AulaPratica aulaPratica) {
        if (aulaPratica.getAluno() == null) {
            throw new RegraNegocioException("Aluno Inválido");
        }
        if (aulaPratica.getInstrutor() == null) {
            throw new RegraNegocioException("Instrutor Inválido");
        }
        if (aulaPratica.getVeiculo() == null) {
            throw new RegraNegocioException("Veículo Inválido");
        }
        if (aulaPratica.getDataAulaPratica() == null) {
            throw new RegraNegocioException("Data Inválida");
        }
        if (aulaPratica.getHorarioAulaPratica() == null) {
            throw new RegraNegocioException("Horário Inválido");
        }
        if (aulaPratica.getCategoria() == null) {
            throw new RegraNegocioException("Categoria Inválido");

        }
    }
}

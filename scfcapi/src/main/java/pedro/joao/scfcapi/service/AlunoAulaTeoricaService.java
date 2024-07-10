package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.repository.AlunoAulaTeoricaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoAulaTeoricaService {
    private AlunoAulaTeoricaRepository repository;
    public AlunoAulaTeoricaService(AlunoAulaTeoricaRepository repository) { this.repository = repository;}
    public List<AlunoAulaTeorica> getAlunoAulaTeorica() {return repository.findAll(); }
    public Optional<AlunoAulaTeorica> getAlunoAulaTeoricaById(Long id) {return repository.findById(id);}

    @Transactional
    public AlunoAulaTeorica salvar(AlunoAulaTeorica alunoAulaTeorica) {
        validar(alunoAulaTeorica);
        return repository.save(alunoAulaTeorica);
    }

    @Transactional
    public void excluir(AlunoAulaTeorica alunoAulaTeorica) {
        Objects.requireNonNull(alunoAulaTeorica.getId());
        repository.delete(alunoAulaTeorica);
    }

    public void validar(AlunoAulaTeorica alunoAulaTeorica) {
        if (alunoAulaTeorica.getAluno() == null) {
            throw new RegraNegocioException("Aluno Inválido");
        }
        if (alunoAulaTeorica.getAulaTeorica() == null) {
            throw new RegraNegocioException("Aula Teórica Inválida");
        }
    }
}

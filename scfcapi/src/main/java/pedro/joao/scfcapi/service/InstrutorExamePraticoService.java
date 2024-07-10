package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.InstrutorExamePratico;
import pedro.joao.scfcapi.model.repository.InstrutorExamePraticoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InstrutorExamePraticoService {
    private InstrutorExamePraticoRepository repository;
    public InstrutorExamePraticoService(InstrutorExamePraticoRepository repository) { this.repository = repository;}
    public List<InstrutorExamePratico> getInstrutorExamePratico() {return repository.findAll(); }
    public Optional<InstrutorExamePratico> getInstrutorExamePraticoById(Long id) {return repository.findById(id);}

    @Transactional
    public InstrutorExamePratico salvar(InstrutorExamePratico instrutorExamePratico) {
        validar(instrutorExamePratico);
        return repository.save(instrutorExamePratico);
    }

     @Transactional
    public void excluir(InstrutorExamePratico instrutorExamePratico) {
        Objects.requireNonNull(instrutorExamePratico.getId());
        repository.delete(instrutorExamePratico);
    }

    public void validar(InstrutorExamePratico instrutorExamePratico) {
        if (instrutorExamePratico.getInstrutor() == null) {
            throw new RegraNegocioException("Instrutor Inválido");
        }
        if (instrutorExamePratico.getExamePratico() == null) {
            throw new RegraNegocioException("Exame Prático Inválido");
        }
    }
}

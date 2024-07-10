package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.repository.ExamePraticoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExamePraticoService {
    private ExamePraticoRepository repository;
    public ExamePraticoService(ExamePraticoRepository repository) { this.repository = repository;}
    public List<ExamePratico> getExamePraticos() {return repository.findAll(); }
    public Optional<ExamePratico> getExamePraticoById(Long id) {return repository.findById(id);}

    @Transactional
    public ExamePratico salvar(ExamePratico examePratico) {
        validar(examePratico);
        return repository.save(examePratico);
    }

     @Transactional
    public void excluir(ExamePratico examePratico) {
        Objects.requireNonNull(examePratico.getId());
        repository.delete(examePratico);
    }

    public void validar(ExamePratico examePratico) {
        if (examePratico.getCategoria() == null) {
            throw new RegraNegocioException("Categoria Inválida");
        }
        if (examePratico.getDataExamePratico() == null) {
            throw new RegraNegocioException("Data Inválida");
        }
        if (examePratico.getHorarioExamePratico() == null) {
            throw new RegraNegocioException("Horário Inválido");
        }
        if (examePratico.getLocal() == null || examePratico.getLocal().trim().equals("")) {
            throw new RegraNegocioException("Local Inválido");
        }
    }
}

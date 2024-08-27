package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExameTeorico;
import pedro.joao.scfcapi.model.repository.ExameTeoricoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExameTeoricoService {
    private ExameTeoricoRepository repository;
    public ExameTeoricoService(ExameTeoricoRepository repository) { this.repository = repository;}
    public List<ExameTeorico> getExameTeoricos() {return repository.findAll(); }
    public Optional<ExameTeorico> getExameTeoricoById(Long id) {return repository.findById(id);}

    @Transactional
    public ExameTeorico salvar(ExameTeorico exameTeorico) {
        validar(exameTeorico);
        return repository.save(exameTeorico);
    }

     @Transactional
    public void excluir(ExameTeorico exameTeorico) {
        Objects.requireNonNull(exameTeorico.getId());
        repository.delete(exameTeorico);
    }

    public void validar(ExameTeorico exameTeorico) {
        if (exameTeorico.getDataExameTeorico() == null) {
            throw new RegraNegocioException("Data Inválida");
        }
        if (exameTeorico.getHorarioExameTeorico() == null) {
            throw new RegraNegocioException("Horário Inválido");
        }
        if (exameTeorico.getLocalExameTeorico() == null || exameTeorico.getLocalExameTeorico().trim().equals("")) {
            throw new RegraNegocioException("Local Inválido");
        }
    }
}

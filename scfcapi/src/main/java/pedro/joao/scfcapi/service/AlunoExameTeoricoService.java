package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AlunoExameTeorico;
import pedro.joao.scfcapi.model.repository.AlunoExameTeoricoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoExameTeoricoService {
    private AlunoExameTeoricoRepository repository;
    public AlunoExameTeoricoService(AlunoExameTeoricoRepository repository) { this.repository = repository;}
    public List<AlunoExameTeorico> getAlunoExameTeorico() {return repository.findAll(); }
    public Optional<AlunoExameTeorico> getAlunoExameTeoricoById(Long id) {return repository.findById(id);}

    @Transactional
    public AlunoExameTeorico salvar(AlunoExameTeorico alunoExameTeorico) {
        validar(alunoExameTeorico);
        return repository.save(alunoExameTeorico);
    }

    public void validar(AlunoExameTeorico alunoExameTeorico) {
        if (alunoExameTeorico.getAluno() == null) {
            throw new RegraNegocioException("Aluno Inválido");
        }
        if (alunoExameTeorico.getExameTeorico() == null) {
            throw new RegraNegocioException("Exame Teórico Inválida");
        }
    }
}

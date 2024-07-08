package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AlunoSimulado;
import pedro.joao.scfcapi.model.repository.AlunoSimuladoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoSimuladoService {
    private AlunoSimuladoRepository repository;
    public AlunoSimuladoService(AlunoSimuladoRepository repository) { this.repository = repository;}
    public List<AlunoSimulado> getAlunoSimulado() {return repository.findAll(); }
    public Optional<AlunoSimulado> getAlunoSimuladoById(Long id) {return repository.findById(id);}

    @Transactional
    public AlunoSimulado salvar(AlunoSimulado alunoSimulado) {
        validar(alunoSimulado);
        return repository.save(alunoSimulado);
    }

    public void validar(AlunoSimulado alunoSimulado) {
        if (alunoSimulado.getAluno() == null) {
            throw new RegraNegocioException("Aluno Inválido");
        }
        if (alunoSimulado.getSimulado() == null) {
            throw new RegraNegocioException("Simulado Inválido");
        }
    }
}

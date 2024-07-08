package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Contrato;
import pedro.joao.scfcapi.model.repository.ContratoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {
    private ContratoRepository repository;
    public ContratoService(ContratoRepository repository) { this.repository = repository;}
    public List<Contrato> getContratos() {return repository.findAll(); }
    public Optional<Contrato> getContratoById(Long id) {return repository.findById(id);}

    @Transactional
    public Contrato salvar(Contrato contrato) {
        validar(contrato);
        return repository.save(contrato);
    }

    public void validar(Contrato contrato) {
        if (contrato.getAluno() == null) {
            throw new RegraNegocioException("Aluno Inválido");
        }
        if (contrato.getCategoria() == null) {
            throw new RegraNegocioException("Categoria Inválida");
        }
        if (contrato.getDataInicio() == null) {
            throw new RegraNegocioException("Data de Início Inválida");
        }
        if (contrato.getDataFim() == null) {
            throw new RegraNegocioException("Data de Fim Inválida");
        }
    }
}

package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.repository.InstrutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstrutorService {
    private InstrutorRepository repository;
    public InstrutorService(InstrutorRepository repository) { this.repository = repository;}
    public List<Instrutor> getInstrutors() {return repository.findAll(); }
    public Optional<Instrutor> getInstrutorById(Long id) {return repository.findById(id);}

    @Transactional
    public Instrutor salvar(Instrutor instrutor) {
        validar(instrutor);
        return repository.save(instrutor);
    }

    public void validar(Instrutor instrutor) {
        if (instrutor.getNome() == null || instrutor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome Inválido");
        }
        if (instrutor.getEmail() == null || instrutor.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email Inválido");
        }
        if (instrutor.getTelefone() == null || instrutor.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone Inválido");
        }
        if (instrutor.getCpf() == null || instrutor.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF Inválido");
        }
        if (instrutor.getLogradouro() == null || instrutor.getLogradouro().trim().equals("")) {
            throw new RegraNegocioException("Logradouro Inválido");
        }
        if (instrutor.getNumero() == null || instrutor.getNumero().trim().equals("")) {
            throw new RegraNegocioException("Número Inválido");
        }
        if (instrutor.getBairro() == null || instrutor.getBairro().trim().equals("")) {
            throw new RegraNegocioException("Bairro Inválido");
        }
        if (instrutor.getCidade() == null || instrutor.getCidade().trim().equals("")) {
            throw new RegraNegocioException("Cidade Inválido");
        }
        if (instrutor.getUf() == null || instrutor.getUf().trim().equals("")) {
            throw new RegraNegocioException("UF Inválido");
        }
        if (instrutor.getCep() == null || instrutor.getCep().trim().equals("")) {
            throw new RegraNegocioException("Cep Inválido");
        }
        if (instrutor.getClt() == null || instrutor.getClt().trim().equals("")) {
            throw new RegraNegocioException("CLT Inválida");
        }
    }
}

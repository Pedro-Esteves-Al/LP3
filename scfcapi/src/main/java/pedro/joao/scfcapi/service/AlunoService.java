package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.repository.AlunoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoService {
    private AlunoRepository repository;
    public AlunoService(AlunoRepository repository) { this.repository = repository;}
    public List<Aluno> getAlunos() {return repository.findAll(); }
    public Optional<Aluno> getAlunoById(Long id) {return repository.findById(id);}

    @Transactional
    public Aluno salvar(Aluno aluno) {
        validar(aluno);
        return repository.save(aluno);
    }

    @Transactional
    public void excluir(Aluno aluno) {
        Objects.requireNonNull(aluno.getId());
        repository.delete(aluno);
    }

    public void validar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome Inválido");
        }
        if (aluno.getEmail() == null || aluno.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email Inválido");
        }
        if (aluno.getTelefone() == null || aluno.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone Inválido");
        }
        if (aluno.getCpf() == null || aluno.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF Inválido");
        }
        if (aluno.getLogradouro() == null || aluno.getLogradouro().trim().equals("")) {
            throw new RegraNegocioException("Logradouro Inválido");
        }
        if (aluno.getNumero() == null || aluno.getNumero().trim().equals("")) {
            throw new RegraNegocioException("Número Inválido");
        }
        if (aluno.getBairro() == null || aluno.getBairro().trim().equals("")) {
            throw new RegraNegocioException("Bairro Inválido");
        }
        if (aluno.getCidade() == null || aluno.getCidade().trim().equals("")) {
            throw new RegraNegocioException("Cidade Inválido");
        }
        if (aluno.getUf() == null || aluno.getUf().trim().equals("")) {
            throw new RegraNegocioException("UF Inválido");
        }
        if (aluno.getCep() == null || aluno.getCep().trim().equals("")) {
            throw new RegraNegocioException("Cep Inválido");
        }
        if (aluno.getContrato() == null) {
            throw new RegraNegocioException("Contrato Inválido");
        }
    }
}

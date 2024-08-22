package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Usuario;
import pedro.joao.scfcapi.model.repository.UsuarioRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> getUsuarios() {
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        validar(usuario);
        return repository.save(usuario);
    }

    @Transactional
    public void excluir(Usuario usuario) {
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }

    public void validar(Usuario usuario) {
        if (usuario.getLogin() == null || usuario.getLogin().trim().equals("")) {
            throw new RegraNegocioException("login inválido");
        }
        if (usuario.getCpf() == null|| usuario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("cpf inválido");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().equals("")) {
            throw new RegraNegocioException("senha inválida");
        }
       /* if (usuario.getAdmin() == null) {
            throw new RegraNegocioException("cpf inválido");
        }*/
    }
}

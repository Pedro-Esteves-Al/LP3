package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private CategoriaRepository repository;
    public CategoriaService(CategoriaRepository repository) { this.repository = repository;}
    public List<Categoria> getCategorias() {return repository.findAll(); }
    public Optional<Categoria> getCategoriaById(Long id) {return repository.findById(id);}

    @Transactional
    public Categoria salvar(Categoria categoria) {
        validar(categoria);
        return repository.save(categoria);
    }

    public void validar(Categoria categoria) {
        if (categoria.getTipo() == null || categoria.getTipo().trim().equals("")) {
            throw new RegraNegocioException("Tipo Inválido");
        }
    }
}

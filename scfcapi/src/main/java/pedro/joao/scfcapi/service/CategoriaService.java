package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
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
}

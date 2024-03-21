package pedro.joao.scfcapi.service;

import org.springframework.stereotype.Service;
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
}

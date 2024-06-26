package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.SimuladoDTO;
import pedro.joao.scfcapi.api.dto.VeiculoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.Simulado;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.InstrutorService;
import pedro.joao.scfcapi.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Veiculos")
@RequiredArgsConstructor
@CrossOrigin

public class VeiculoController {
    private final VeiculoService service;
    private final CategoriaService categoriaService;
    private final InstrutorService instrutorService;

    @GetMapping()
    public ResponseEntity get() {
        List<Veiculo> veiculos = service.getVeiculos();
        return ResponseEntity.ok(veiculos.stream().map(VeiculoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if(!veiculo.isPresent()) {
            return new ResponseEntity("Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(veiculo.map(VeiculoDTO::create));
    }
    
    public Veiculo converter(VeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                veiculo.setCategoria(null);
            } else {
                veiculo.setCategoria(cateogria.get());
            }
        }
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> cateogria = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!cateogria.isPresent()) {
                veiculo.setInstrutor(null);
            } else {
                veiculo.setInstrutor(cateogria.get());
            }
        }
        return veiculo;
    }
}

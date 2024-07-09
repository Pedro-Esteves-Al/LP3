package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.CategoriaDTO;
import pedro.joao.scfcapi.api.dto.SimuladoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.Simulado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.joao.scfcapi.service.SimuladoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/simulados")
@RequiredArgsConstructor
@CrossOrigin

public class SimuladoController {

    private final SimuladoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Simulado> simulados = service.getSimulados();
        return ResponseEntity.ok(simulados.stream().map(SimuladoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Simulado> simulado = service.getSimuladoById(id);
        if(!simulado.isPresent()) {
            return new ResponseEntity("Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(simulado.map(SimuladoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SimuladoDTO dto) {
        try {
            Simulado simulado = converter(dto);
            simulado = service.salvar(simulado);
            return new ResponseEntity(simulado,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody SimuladoDTO dto) {
        if (!service.getSimuladoById(id).isPresent()) {
            return new ResponseEntity("Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Simulado simulado = converter(dto);
            simulado.setId(id);
            service.salvar(simulado);
            return ResponseEntity.ok(simulado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Simulado converter(SimuladoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Simulado simulado = modelMapper.map(dto, Simulado.class);
        return simulado;
    }
}

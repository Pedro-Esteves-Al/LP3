package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AulaTeoricaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.service.AulaTeoricaService;
import pedro.joao.scfcapi.service.InstrutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aulasTeoricas")
@RequiredArgsConstructor
@CrossOrigin

public class AulaTeoricaController {
    private final AulaTeoricaService service;
    private final InstrutorService instrutorService;

    @GetMapping()
    public ResponseEntity get() {
        List<AulaTeorica> aulaTeoricas = service.getAulaTeoricas();
        return ResponseEntity.ok(aulaTeoricas.stream().map(AulaTeoricaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AulaTeorica> aulaTeorica = service.getAulaTeoricaById(id);
        if(!aulaTeorica.isPresent()) {
            return new ResponseEntity("Aula Teórica não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(aulaTeorica.map(AulaTeoricaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AulaTeoricaDTO dto) {
        try {
            AulaTeorica aulaTeorica = converter(dto);
            aulaTeorica = service.salvar(aulaTeorica);
            return new ResponseEntity(aulaTeorica,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AulaTeoricaDTO dto) {
        if (!service.getAulaTeoricaById(id).isPresent()) {
            return new ResponseEntity("Aula Teórica não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            AulaTeorica aulaTeorica = converter(dto);
            aulaTeorica.setId(id);
            service.salvar(aulaTeorica);
            return ResponseEntity.ok(aulaTeorica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AulaTeorica> aulaTeorica = service.getAulaTeoricaById(id);
        if (!aulaTeorica.isPresent()) {
            return new ResponseEntity("Aula Teórica não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(aulaTeorica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AulaTeorica converter(AulaTeoricaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AulaTeorica aulaTeorica = modelMapper.map(dto, AulaTeorica.class);
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> instrutor = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!instrutor.isPresent()) {
                aulaTeorica.setInstrutor(null);
            } else {
                aulaTeorica.setInstrutor(instrutor.get());
            }
        }
        return aulaTeorica;
    }
}

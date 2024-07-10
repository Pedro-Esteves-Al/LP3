package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.InstrutorDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Instrutor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.joao.scfcapi.service.InstrutorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/instrutores")
@RequiredArgsConstructor
@CrossOrigin

public class InstrutorController {

    private final InstrutorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Instrutor> instrutores = service.getInstrutors();
        return ResponseEntity.ok(instrutores.stream().map(InstrutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Instrutor> instrutor = service.getInstrutorById(id);
        if(!instrutor.isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(instrutor.map(InstrutorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody InstrutorDTO dto) {
        try {
            Instrutor instrutor = converter(dto);
            instrutor = service.salvar(instrutor);
            return new ResponseEntity(instrutor,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody InstrutorDTO dto) {
        if (!service.getInstrutorById(id).isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Instrutor instrutor = converter(dto);
            instrutor.setId(id);
            service.salvar(instrutor);
            return ResponseEntity.ok(instrutor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Instrutor> instrutor = service.getInstrutorById(id);
        if (!instrutor.isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(instrutor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Instrutor converter(InstrutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Instrutor instrutor = modelMapper.map(dto, Instrutor.class);
        return instrutor;
    }
}

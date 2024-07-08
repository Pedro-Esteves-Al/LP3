package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.InstrutorDTO;
import pedro.joao.scfcapi.api.dto.InstrutorExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.InstrutorExamePratico;
import pedro.joao.scfcapi.service.ExamePraticoService;
import pedro.joao.scfcapi.service.InstrutorExamePraticoService;
import pedro.joao.scfcapi.service.InstrutorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/intrutoresExamesPraticos")
@RequiredArgsConstructor
@CrossOrigin

public class InstrutorExamePraticaController {
    private final InstrutorExamePraticoService service;
    private final InstrutorService instrutorService;
    private final ExamePraticoService examePraticoService;

    @GetMapping()
    public ResponseEntity get() {
        List<InstrutorExamePratico> instrutorExamePraticos = service.getInstrutorExamePratico();
        return ResponseEntity.ok(instrutorExamePraticos.stream().map(InstrutorExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<InstrutorExamePratico> instrutorExamePratico = service.getInstrutorExamePraticoById(id);
        if(!instrutorExamePratico.isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(instrutorExamePratico.map(InstrutorExamePraticoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody InstrutorExamePraticoDTO dto) {
        try {
            InstrutorExamePratico instrutorExamePratico = converter(dto);
            instrutorExamePratico = service.salvar(instrutorExamePratico);
            return new ResponseEntity(instrutorExamePratico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public InstrutorExamePratico converter(InstrutorExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        InstrutorExamePratico instrutorExamePratico = modelMapper.map(dto, InstrutorExamePratico.class);
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> instrutor = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!instrutor.isPresent()) {
                instrutorExamePratico.setInstrutor(null);
            } else {
                instrutorExamePratico.setInstrutor(instrutor.get());
            }
        }
        if (dto.getIdExamePratico() != null) {
            Optional<ExamePratico> examePratico = examePraticoService.getExamePraticoById(dto.getIdExamePratico());
            if (!examePratico.isPresent()) {
                instrutorExamePratico.setExamePratico(null);
            } else {
                instrutorExamePratico.setExamePratico(examePratico.get());
            }
        }
        return instrutorExamePratico;
    }
}

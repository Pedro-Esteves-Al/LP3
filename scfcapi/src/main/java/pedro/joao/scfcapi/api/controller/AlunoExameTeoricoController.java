package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.AlunoExamePraticoDTO;
import pedro.joao.scfcapi.api.dto.AlunoExameTeoricoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.*;
import pedro.joao.scfcapi.service.AlunoExameTeoricoService;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.ExameTeoricoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alunosExamesTeoricos")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoExameTeoricoController {
    private final AlunoExameTeoricoService service;
    private final AlunoService alunoService;
    private final ExameTeoricoService exameTeoricoService;

    @GetMapping()
    public ResponseEntity get() {
        List<AlunoExameTeorico> alunoExameTeoricos = service.getAlunoExameTeorico();
        return ResponseEntity.ok(alunoExameTeoricos.stream().map(AlunoExameTeoricoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AlunoExameTeorico> alunoExameTeorico = service.getAlunoExameTeoricoById(id);
        if (!alunoExameTeorico.isPresent()) {
            return new ResponseEntity("Relação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoExameTeorico.map(AlunoExameTeoricoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AlunoExameTeoricoDTO dto) {
        try {
            AlunoExameTeorico alunoExameTeorico = converter(dto);
            alunoExameTeorico = service.salvar(alunoExameTeorico);
            return new ResponseEntity(alunoExameTeorico, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"id"})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AlunoExameTeoricoDTO dto) {
        if (!service.getAlunoExameTeoricoById(id).isPresent()) {
            return new ResponseEntity("Relação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            AlunoExameTeorico alunoExameTeorico = converter(dto);
            alunoExameTeorico.setId(id);
            service.salvar(alunoExameTeorico);
            return ResponseEntity.ok(alunoExameTeorico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AlunoExameTeorico converter(AlunoExameTeoricoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoExameTeorico alunoExameTeorico = modelMapper.map(dto, AlunoExameTeorico.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoExameTeorico.setAluno(null);
            } else {
                alunoExameTeorico.setAluno(aluno.get());
            }
        }
        if (dto.getIdExameTeorico() != null) {
            Optional<ExameTeorico> exameTeorico = exameTeoricoService.getExameTeoricoById(dto.getIdExameTeorico());
            if (!exameTeorico.isPresent()) {
                alunoExameTeorico.setExameTeorico(null);
            } else {
                alunoExameTeorico.setExameTeorico(exameTeorico.get());
            }
        }
        return alunoExameTeorico;
    }
}
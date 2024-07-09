package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.AlunoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;

import pedro.joao.scfcapi.api.dto.AlunoExamePraticoDTO;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AlunoExamePratico;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.service.AlunoExamePraticoService;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.ExamePraticoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alunosExamesPraticos")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoExamePraticoController {
    public final AlunoExamePraticoService service;
    public final ExamePraticoService examePraticoService;
    public final AlunoService alunoService;

    @GetMapping()
    public ResponseEntity get() {
        List<AlunoExamePratico> alunoExamePraticos = service.getAlunoExamePratico();
        return ResponseEntity.ok(alunoExamePraticos.stream().map(AlunoExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AlunoExamePratico> alunoExamePratico = service.getAlunoExamePraticoById(id);
        if(!alunoExamePratico.isPresent()) {
            return new ResponseEntity("Relação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoExamePratico.map(AlunoExamePraticoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AlunoExamePraticoDTO dto) {
        try {
            AlunoExamePratico alunoExamePratico = converter(dto);
            alunoExamePratico = service.salvar(alunoExamePratico);
            return new ResponseEntity(alunoExamePratico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody AlunoExamePraticoDTO dto) {
        if(!service.getAlunoExamePraticoById(id).isPresent()) {
            return new ResponseEntity("Relação não encontrada",HttpStatus.NOT_FOUND);
        }
        try {
            AlunoExamePratico alunoExamePratico = converter(dto);
            alunoExamePratico.setId(id);
            service.salvar(alunoExamePratico);
            return ResponseEntity.ok(alunoExamePratico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AlunoExamePratico converter(AlunoExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoExamePratico alunoExamePratico = modelMapper.map(dto, AlunoExamePratico.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoExamePratico.setAluno(null);
            } else {
                alunoExamePratico.setAluno(aluno.get());
            }
        }
        if (dto.getIdExamePratico() != null) {
            Optional<ExamePratico> examePratico = examePraticoService.getExamePraticoById(dto.getIdExamePratico());
            if (!examePratico.isPresent()) {
                alunoExamePratico.setExamePratico(null);
            } else {
                alunoExamePratico.setExamePratico(examePratico.get());
            }
        }
        return alunoExamePratico;
    }
}

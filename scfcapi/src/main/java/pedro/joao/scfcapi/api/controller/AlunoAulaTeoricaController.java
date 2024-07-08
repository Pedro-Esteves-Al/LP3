package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.exception.RegraNegocioException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pedro.joao.scfcapi.api.dto.AlunoAulaTeoricaDTO;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.service.AlunoAulaTeoricaService;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.AulaTeoricaService;

@RestController
@RequestMapping("/api/v1/alunosAulasTeoricas")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoAulaTeoricaController {
    private final AlunoAulaTeoricaService service;
    private final AlunoService alunoService;
    private final AulaTeoricaService aulaTeoricaService;

    @GetMapping()
    public ResponseEntity get() {
        List<AlunoAulaTeorica> alunoAulaTeoricas = service.getAlunoAulaTeorica();
        return ResponseEntity.ok(alunoAulaTeoricas.stream().map(AlunoAulaTeoricaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AlunoAulaTeorica> alunoAulaTeorica = service.getAlunoAulaTeoricaById(id);
        if(!alunoAulaTeorica.isPresent()) {
            return new ResponseEntity("Alunos não encontrados", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoAulaTeorica.map(AlunoAulaTeoricaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AlunoAulaTeoricaDTO dto) {
        try {
            AlunoAulaTeorica alunoAulaTeorica = converter(dto);
            alunoAulaTeorica = service.salvar(alunoAulaTeorica);
            return new ResponseEntity(alunoAulaTeorica,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AlunoAulaTeorica converter(AlunoAulaTeoricaDTO dto) {

        ModelMapper modelMapper = new ModelMapper();
        AlunoAulaTeorica alunoAulaTeorica = modelMapper.map(dto, AlunoAulaTeorica.class);

        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoAulaTeorica.setAluno(null);
            } else {
                alunoAulaTeorica.setAluno(aluno.get());
            }
        }

        if (dto.getIdAulaTeorica() != null) {
            Optional<AulaTeorica> aulaTeorica = aulaTeoricaService.getAulaTeoricaById(dto.getIdAulaTeorica());
            if (!aulaTeorica.isPresent()) {
                alunoAulaTeorica.setAulaTeorica(null);
            } else {
                alunoAulaTeorica.setAulaTeorica(aulaTeorica.get());
            }
        }

        return alunoAulaTeorica;
    }
}

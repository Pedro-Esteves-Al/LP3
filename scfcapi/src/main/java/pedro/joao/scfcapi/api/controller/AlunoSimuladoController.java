package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.AlunoAulaTeoricaDTO;
import pedro.joao.scfcapi.api.dto.AlunoExameTeoricoDTO;
import pedro.joao.scfcapi.api.dto.AlunoSimuladoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.*;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.AlunoSimuladoService;
import pedro.joao.scfcapi.service.SimuladoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alunosSimulados")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoSimuladoController {
    private final AlunoSimuladoService service;
    private final AlunoService alunoService;
    private final SimuladoService simuladoService;

    @GetMapping()
    public ResponseEntity get() {
        List<AlunoSimulado> alunoSimulados = service.getAlunoSimulado();
        return ResponseEntity.ok(alunoSimulados.stream().map(AlunoSimuladoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AlunoSimulado> alunoSimulado = service.getAlunoSimuladoById(id);
        if(!alunoSimulado.isPresent()) {
            return new ResponseEntity("Alunos não encontrados", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoSimulado.map(AlunoSimuladoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AlunoSimuladoDTO dto) {
        try {
            AlunoSimulado alunoSimulado = converter(dto);
            alunoSimulado = service.salvar(alunoSimulado);
            return new ResponseEntity(alunoSimulado,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AlunoSimulado converter(AlunoSimuladoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoSimulado alunoSimulado = modelMapper.map(dto, AlunoSimulado.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoSimulado.setAluno(null);
            } else {
                alunoSimulado.setAluno(aluno.get());
            }
        }
        if (dto.getIdSimulado() != null) {
            Optional<Simulado> simulado = simuladoService.getSimuladoById(dto.getIdSimulado());
            if (!simulado.isPresent()) {
                alunoSimulado.setSimulado(null);
            } else {
                alunoSimulado.setSimulado(simulado.get());
            }
        }
        return alunoSimulado;
    }
}

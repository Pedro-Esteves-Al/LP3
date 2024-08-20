package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@Api("API de Cursos")

public class AlunoSimuladoController {
    private final AlunoSimuladoService service;
    private final AlunoService alunoService;
    private final SimuladoService simuladoService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Alunos relacionados a Simulados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<AlunoSimulado> alunoSimulados = service.getAlunoSimulado();
        return ResponseEntity.ok(alunoSimulados.stream().map(AlunoSimuladoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um aluno e um simulado que ele participa ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Simulado encontrado"),
            @ApiResponse(code = 404, message = "Aluno/Simulado não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AlunoSimulado> alunoSimulado = service.getAlunoSimuladoById(id);
        if(!alunoSimulado.isPresent()) {
            return new ResponseEntity("Aluno ou Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoSimulado.map(AlunoSimuladoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva a participação de um aluno no simulado ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Simulado salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Aluno/Simulado")
    })
    public ResponseEntity post(@RequestBody AlunoSimuladoDTO dto) {
        try {
            AlunoSimulado alunoSimulado = converter(dto);
            alunoSimulado = service.salvar(alunoSimulado);
            return new ResponseEntity(alunoSimulado,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita a participação de um aluno em um simulado ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Simulado editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar Aluno/Simulado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AlunoSimuladoDTO dto) {
        if (!service.getAlunoSimuladoById(id).isPresent()) {
            return new ResponseEntity("Aluno ou Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AlunoSimulado alunoSimulado = converter(dto);
            alunoSimulado.setId(id);
            service.salvar(alunoSimulado);
            return ResponseEntity.ok(alunoSimulado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta a participação de um aluno em um simulado ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Simulado deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar Aluno/Simulado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AlunoSimulado> alunoSimulado = service.getAlunoSimuladoById(id);
        if (!alunoSimulado.isPresent()) {
            return new ResponseEntity("Aluno ou Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(alunoSimulado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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

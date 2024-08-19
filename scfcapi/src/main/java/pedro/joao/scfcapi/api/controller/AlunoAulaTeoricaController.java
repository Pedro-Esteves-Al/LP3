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
@Api("API de Cursos")

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
    @ApiOperation("Obter detalhes de um aluno e uma aula teórica que ele participa ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Aula Teórica encontrado"),
            @ApiResponse(code = 404, message = "Aluno/Aula Teórica não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AlunoAulaTeorica> alunoAulaTeorica = service.getAlunoAulaTeoricaById(id);
        if(!alunoAulaTeorica.isPresent()) {
            return new ResponseEntity("Aluno ou Aula Teórica não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoAulaTeorica.map(AlunoAulaTeoricaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva a participação de um aluno na aula teórica ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Aula Teórica salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Aluno/Aula Teórica")
    })
    public ResponseEntity post(@RequestBody AlunoAulaTeoricaDTO dto) {
        try {
            AlunoAulaTeorica alunoAulaTeorica = converter(dto);
            alunoAulaTeorica = service.salvar(alunoAulaTeorica);
            return new ResponseEntity(alunoAulaTeorica,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita a participação de um aluno em uma aula teórica ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Aula Teórica editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar Aluno/Aula Teórica")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody AlunoAulaTeoricaDTO dto) {
        if(!service.getAlunoAulaTeoricaById(id).isPresent()) {
            return new ResponseEntity("Aluno ou Aula Teórica não encontrada",HttpStatus.NOT_FOUND);
        }
        try {
            AlunoAulaTeorica alunoAulaTeorica = converter(dto);
            alunoAulaTeorica.setId(id);
            service.salvar(alunoAulaTeorica);
            return ResponseEntity.ok(alunoAulaTeorica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta a participação de um aluno em uma aula teórica ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Aula Teórica deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar Aluno/Aula Teórica")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AlunoAulaTeorica> alunoAulaTeorica = service.getAlunoAulaTeoricaById(id);
        if (!alunoAulaTeorica.isPresent()) {
            return new ResponseEntity("Aluno ou Aula Teórica não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(alunoAulaTeorica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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

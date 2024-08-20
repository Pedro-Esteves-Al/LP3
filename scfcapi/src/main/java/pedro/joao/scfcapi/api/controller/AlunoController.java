package pedro.joao.scfcapi.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pedro.joao.scfcapi.api.dto.AlunoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.joao.scfcapi.service.AlunoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Cursos")

public class AlunoController {

    private final AlunoService service;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Alunos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<Aluno> alunos = service.getAlunos();
        return ResponseEntity.ok(alunos.stream().map(AlunoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno encontrado"),
            @ApiResponse(code = 404, message = "Aluno não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Aluno> aluno = service.getAlunoById(id);
        if(!aluno.isPresent()) {
            return new ResponseEntity("Aluno não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(aluno.map(AlunoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Aluno")
    })
    public ResponseEntity post(@RequestBody AlunoDTO dto) {
        try {
            Aluno aluno = converter(dto);
            aluno = service.salvar(aluno);
            return new ResponseEntity(aluno,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Aluno")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody AlunoDTO dto) {
        if(!service.getAlunoById(id).isPresent()) {
            return new ResponseEntity("Aluno não encontrado",HttpStatus.NOT_FOUND);
        }
        try {
            Aluno aluno = converter(dto);
            aluno.setId(id);
            service.salvar(aluno);
            return ResponseEntity.ok(aluno);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Aluno")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Aluno> aluno = service.getAlunoById(id);
        if (!aluno.isPresent()) {
            return new ResponseEntity("Aluno não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(aluno.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Aluno converter(AlunoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = modelMapper.map(dto, Aluno.class);
        return aluno;
    }
}

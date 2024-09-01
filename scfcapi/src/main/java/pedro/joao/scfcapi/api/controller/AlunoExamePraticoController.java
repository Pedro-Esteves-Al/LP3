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
@Api("API de relação de Alunos com Exames práticos")

public class AlunoExamePraticoController {
    public final AlunoExamePraticoService service;
    public final ExamePraticoService examePraticoService;
    public final AlunoService alunoService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Alunos relacionados a Exames Práticos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<AlunoExamePratico> alunoExamePraticos = service.getAlunoExamePratico();
        return ResponseEntity.ok(alunoExamePraticos.stream().map(AlunoExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Aluno e um Exame Prático que ele participa ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Exame Prático encontrado"),
            @ApiResponse(code = 404, message = "Aluno/Exame Prático não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AlunoExamePratico> alunoExamePratico = service.getAlunoExamePraticoById(id);
        if(!alunoExamePratico.isPresent()) {
            return new ResponseEntity("Aluno ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alunoExamePratico.map(AlunoExamePraticoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva a participação de um Aluno no Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Exame Prático salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Aluno/Exame Prático")
    })
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
    @ApiOperation("Edita a participação de um Aluno em um Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Exame Prático editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar Aluno/Exame Prático")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody AlunoExamePraticoDTO dto) {
        if(!service.getAlunoExamePraticoById(id).isPresent()) {
            return new ResponseEntity("Aluno ou Exame Prático não encontrado",HttpStatus.NOT_FOUND);
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

    @DeleteMapping("{id}")
    @ApiOperation("Deleta a participação de um Aluno em um Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno/Exame Prático deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar Aluno/Exame Prático")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AlunoExamePratico> alunoExamePratico = service.getAlunoExamePraticoById(id);
        if (!alunoExamePratico.isPresent()) {
            return new ResponseEntity("Aluno ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(alunoExamePratico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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

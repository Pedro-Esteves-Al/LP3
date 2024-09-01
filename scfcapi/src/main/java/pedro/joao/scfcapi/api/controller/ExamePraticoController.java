package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.ExamePraticoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/examesPraticos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Exames práticos")

public class ExamePraticoController {
    public final ExamePraticoService service;
    public final CategoriaService categoriaService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Exames práticos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<ExamePratico> examePraticos = service.getExamePraticos();
        return ResponseEntity.ok(examePraticos.stream().map(ExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Exame Prático")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Prático encontrado"),
            @ApiResponse(code = 404, message = "Exame Prático não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<ExamePratico> examePratico = service.getExamePraticoById(id);
        if(!examePratico.isPresent()) {
            return new ResponseEntity("Exame prático não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(examePratico.map(ExamePraticoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Exame Prático")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Prático salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Exame Prático")
    })
    public ResponseEntity post(@RequestBody ExamePraticoDTO dto) {
        try {
            ExamePratico examePratico = converter(dto);
            examePratico = service.salvar(examePratico);
            return new ResponseEntity(examePratico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Exame Prático")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Prático editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Exame Prático")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody ExamePraticoDTO dto) {
        if (!service.getExamePraticoById(id).isPresent()) {
            return new ResponseEntity("Exame prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ExamePratico examePratico = converter(dto);
            examePratico.setId(id);
            service.salvar(examePratico);
            return ResponseEntity.ok(examePratico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Exame Prático")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Prático deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Exame Prático")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ExamePratico> examePratico = service.getExamePraticoById(id);
        if (!examePratico.isPresent()) {
            return new ResponseEntity("Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(examePratico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    public ExamePratico converter(ExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ExamePratico examePratico = modelMapper.map(dto, ExamePratico.class);
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                examePratico.setCategoria(null);
            } else {
                examePratico.setCategoria(cateogria.get());
            }
        }
        return examePratico;
    }
}

package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ExameTeoricoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.ExameTeorico;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.ExameTeoricoService;
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
@RequestMapping("/api/v1/examesTeoricos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Cursos")

public class ExameTeoricoController {
    private final ExameTeoricoService service;
    private final CategoriaService categoriaService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Exames Teóricos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<ExameTeorico> examesTeoricos = service.getExameTeoricos();
        return ResponseEntity.ok(examesTeoricos.stream().map(ExameTeoricoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Exame Teórico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Teórico encontrado"),
            @ApiResponse(code = 404, message = "Exame Teórico não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<ExameTeorico> exameTeorico = service.getExameTeoricoById(id);
        if(!exameTeorico.isPresent()) {
            return new ResponseEntity("Exame teórico não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exameTeorico.map(ExameTeoricoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Exame Teórico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Teórico salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Exame Teórico")
    })
    public ResponseEntity post(@RequestBody ExameTeoricoDTO dto) {
        try {
            ExameTeorico exameTeorico = converter(dto);
            exameTeorico = service.salvar(exameTeorico);
            return new ResponseEntity(exameTeorico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Exame Teórico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Teórico editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Exame Teórico")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody ExameTeoricoDTO dto) {
        if (!service.getExameTeoricoById(id).isPresent()) {
            return new ResponseEntity("Exame teórico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ExameTeorico exameTeorico = converter(dto);
            exameTeorico.setId(id);
            service.salvar(exameTeorico);
            return ResponseEntity.ok(exameTeorico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Exame Teórico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exame Teórico deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Examte Teórico")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ExameTeorico> exameTeorico = service.getExameTeoricoById(id);
        if (!exameTeorico.isPresent()) {
            return new ResponseEntity("Exame Teórico não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(exameTeorico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ExameTeorico converter(ExameTeoricoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ExameTeorico exameTeorico = modelMapper.map(dto, ExameTeorico.class);
        /*if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                exameTeorico.setCategoria(null);
            } else {
                exameTeorico.setCategoria(cateogria.get());
            }
        }*/
        return exameTeorico;
    }
}

package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ContratoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.Contrato;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.ContratoService;
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
@RequestMapping("/api/v1/contratos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Cursos")

public class ContratoController {
    private final ContratoService service;
    private final AlunoService alunoService;
    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity get() {
        List<Contrato> contratos = service.getContratos();
        return ResponseEntity.ok(contratos.stream().map(ContratoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Contrato")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Contrato encontrado"),
            @ApiResponse(code = 404, message = "Contrato não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Contrato> contrato = service.getContratoById(id);
        if(!contrato.isPresent()) {
            return new ResponseEntity("Contrato não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(contrato.map(ContratoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Contrato")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Contrato salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Contrato")
    })
    public ResponseEntity post(@RequestBody ContratoDTO dto) {
        try {
            Contrato contrato = converter(dto);
            contrato = service.salvar(contrato);
            return new ResponseEntity(contrato,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Contrato")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Contrato editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Contrato")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody ContratoDTO dto) {
        if (!service.getContratoById(id).isPresent()) {
            return new ResponseEntity("Contrato não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Contrato contrato = converter(dto);
            contrato.setId(id);
            service.salvar(contrato);
            return ResponseEntity.ok(contrato);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Contrato")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Contrato deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Contrato")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Contrato> contrato = service.getContratoById(id);
        if (!contrato.isPresent()) {
            return new ResponseEntity("Contrato não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(contrato.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Contrato converter(ContratoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Contrato contrato = modelMapper.map(dto, Contrato.class);
         if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                contrato.setAluno(null);
            } else {
                contrato.setAluno(aluno.get());
            }
        }
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                contrato.setCategoria(null);
            } else {
                contrato.setCategoria(cateogria.get());
            }
        }
        return contrato;
    }
}

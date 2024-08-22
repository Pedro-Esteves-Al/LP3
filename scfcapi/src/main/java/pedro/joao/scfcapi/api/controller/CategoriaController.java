package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.CategoriaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pedro.joao.scfcapi.service.CategoriaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Categorias")

public class CategoriaController {
    
    private final CategoriaService service;

    @GetMapping()
    @ApiOperation("Obter a lista de todas as Categorias cadastradas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategorias();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if(!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria salva com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar a Categoria")
    })
    public ResponseEntity post(@RequestBody CategoriaDTO dto) {
        try {
            Categoria categoria = converter(dto);
            categoria = service.salvar(categoria);
            return new ResponseEntity(categoria,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de uma Categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria editada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar a Categoria")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
        if (!service.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Categoria categoria = converter(dto);
            categoria.setId(id);
            service.salvar(categoria);
            return ResponseEntity.ok(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma Categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria deletada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar a Categoria")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Categoria converter(CategoriaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Categoria categoria = modelMapper.map(dto, Categoria.class);
        return categoria;
    }
}

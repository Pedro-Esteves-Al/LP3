package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.VeiculoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.InstrutorService;
import pedro.joao.scfcapi.service.VeiculoService;
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
@RequestMapping("/api/v1/veiculos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Cursos")

public class VeiculoController {
    private final VeiculoService service;
    private final CategoriaService categoriaService;
    private final InstrutorService instrutorService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Veículos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<Veiculo> veiculos = service.getVeiculos();
        return ResponseEntity.ok(veiculos.stream().map(VeiculoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Veículo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo encontrado"),
            @ApiResponse(code = 404, message = "Veículo não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if(!veiculo.isPresent()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(veiculo.map(VeiculoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Veículo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Veículo")
    })
    public ResponseEntity post(@RequestBody VeiculoDTO dto) {
        try {
            Veiculo veiculo = converter(dto);
            veiculo = service.salvar(veiculo);
            return new ResponseEntity(veiculo,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Veículo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Veículo")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody VeiculoDTO dto) {
        if (!service.getVeiculoById(id).isPresent()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Veiculo veiculo = converter(dto);
            veiculo.setId(id);
            service.salvar(veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Veículo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Veículo")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if (!veiculo.isPresent()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(veiculo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    public Veiculo converter(VeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                veiculo.setCategoria(null);
            } else {
                veiculo.setCategoria(cateogria.get());
            }
        }
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> instrutor = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!instrutor.isPresent()) {
                veiculo.setInstrutor(null);
            } else {
                veiculo.setInstrutor(instrutor.get());
            }
        }
        return veiculo;
    }
}

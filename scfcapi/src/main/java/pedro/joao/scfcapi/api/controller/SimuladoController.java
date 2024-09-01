package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.SimuladoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Simulado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pedro.joao.scfcapi.service.SimuladoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/simulados")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Simulados")

public class SimuladoController {

    private final SimuladoService service;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Simulados cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<Simulado> simulados = service.getSimulados();
        return ResponseEntity.ok(simulados.stream().map(SimuladoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Simulado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulado encontrado"),
            @ApiResponse(code = 404, message = "Simulado não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Simulado> simulado = service.getSimuladoById(id);
        if(!simulado.isPresent()) {
            return new ResponseEntity("Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(simulado.map(SimuladoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Simulado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulado salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Simulado")
    })
    public ResponseEntity post(@RequestBody SimuladoDTO dto) {
        try {
            Simulado simulado = converter(dto);
            simulado = service.salvar(simulado);
            return new ResponseEntity(simulado,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Simulado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulado editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Simulado")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody SimuladoDTO dto) {
        if (!service.getSimuladoById(id).isPresent()) {
            return new ResponseEntity("Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Simulado simulado = converter(dto);
            simulado.setId(id);
            service.salvar(simulado);
            return ResponseEntity.ok(simulado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Simulado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Simulado deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Simulado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Simulado> simulado = service.getSimuladoById(id);
        if (!simulado.isPresent()) {
            return new ResponseEntity("Simulado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(simulado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Simulado converter(SimuladoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Simulado simulado = modelMapper.map(dto, Simulado.class);
        return simulado;
    }
}

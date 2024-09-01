package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.InstrutorDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Instrutor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pedro.joao.scfcapi.service.InstrutorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/instrutores")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Instrutores")

public class InstrutorController {

    private final InstrutorService service;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Instrutores cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<Instrutor> instrutores = service.getInstrutors();
        return ResponseEntity.ok(instrutores.stream().map(InstrutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Instrutor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor encontrado"),
            @ApiResponse(code = 404, message = "Instrutor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Instrutor> instrutor = service.getInstrutorById(id);
        if(!instrutor.isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(instrutor.map(InstrutorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Instrutor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Instrutor")
    })
    public ResponseEntity post(@RequestBody InstrutorDTO dto) {
        try {
            Instrutor instrutor = converter(dto);
            instrutor = service.salvar(instrutor);
            return new ResponseEntity(instrutor,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de um Instrutor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Instrutor")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody InstrutorDTO dto) {
        if (!service.getInstrutorById(id).isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Instrutor instrutor = converter(dto);
            instrutor.setId(id);
            service.salvar(instrutor);
            return ResponseEntity.ok(instrutor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Instrutor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Instrutor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Instrutor> instrutor = service.getInstrutorById(id);
        if (!instrutor.isPresent()) {
            return new ResponseEntity("Instrutor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(instrutor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Instrutor converter(InstrutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Instrutor instrutor = modelMapper.map(dto, Instrutor.class);
        return instrutor;
    }
}

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
import pedro.joao.scfcapi.api.dto.InstrutorExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.InstrutorExamePratico;
import pedro.joao.scfcapi.service.ExamePraticoService;
import pedro.joao.scfcapi.service.InstrutorExamePraticoService;
import pedro.joao.scfcapi.service.InstrutorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/intrutoresExamesPraticos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de relação de Instrutores com Exames práticos")

public class InstrutorExamePraticoController {
    private final InstrutorExamePraticoService service;
    private final InstrutorService instrutorService;
    private final ExamePraticoService examePraticoService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Instrutores relacionados a Exames Práticos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<InstrutorExamePratico> instrutorExamePraticos = service.getInstrutorExamePratico();
        return ResponseEntity.ok(instrutorExamePraticos.stream().map(InstrutorExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Instrutor e um Exame Prático que ele participa ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor/Exame Prático encontrado"),
            @ApiResponse(code = 404, message = "Instrutor/Exame Prático não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<InstrutorExamePratico> instrutorExamePratico = service.getInstrutorExamePraticoById(id);
        if(!instrutorExamePratico.isPresent()) {
            return new ResponseEntity("Instrutor ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(instrutorExamePratico.map(InstrutorExamePraticoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva a participação de um Instrutor no Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor/Exame Prático salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Instrutor/Exame Prático")
    })
    public ResponseEntity post(@RequestBody InstrutorExamePraticoDTO dto) {
        try {
            InstrutorExamePratico instrutorExamePratico = converter(dto);
            instrutorExamePratico = service.salvar(instrutorExamePratico);
            return new ResponseEntity(instrutorExamePratico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita a participação de um Instrutor em um Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor/Exame Prático editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar Instrutor/Exame Prático")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody InstrutorExamePraticoDTO dto) {
        if (!service.getInstrutorExamePraticoById(id).isPresent()) {
            return new ResponseEntity("Instrutor ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            InstrutorExamePratico instrutorExamePratico = converter(dto);
            instrutorExamePratico.setId(id);
            service.salvar(instrutorExamePratico);
            return ResponseEntity.ok(instrutorExamePratico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta a participação de um Instrutor em um Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Instrutor/Exame Prático deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar Instrutor/Exame Prático")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<InstrutorExamePratico> instrutorExamePratico = service.getInstrutorExamePraticoById(id);
        if (!instrutorExamePratico.isPresent()) {
            return new ResponseEntity("Instrutor ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(instrutorExamePratico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public InstrutorExamePratico converter(InstrutorExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        InstrutorExamePratico instrutorExamePratico = modelMapper.map(dto, InstrutorExamePratico.class);
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> instrutor = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!instrutor.isPresent()) {
                instrutorExamePratico.setInstrutor(null);
            } else {
                instrutorExamePratico.setInstrutor(instrutor.get());
            }
        }
        if (dto.getIdExamePratico() != null) {
            Optional<ExamePratico> examePratico = examePraticoService.getExamePraticoById(dto.getIdExamePratico());
            if (!examePratico.isPresent()) {
                instrutorExamePratico.setExamePratico(null);
            } else {
                instrutorExamePratico.setExamePratico(examePratico.get());
            }
        }
        return instrutorExamePratico;
    }
}

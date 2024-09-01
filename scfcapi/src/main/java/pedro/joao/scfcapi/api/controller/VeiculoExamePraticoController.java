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
import pedro.joao.scfcapi.api.dto.VeiculoExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.model.entity.VeiculoExamePratico;
import pedro.joao.scfcapi.service.ExamePraticoService;
import pedro.joao.scfcapi.service.VeiculoExamePraticoService;
import pedro.joao.scfcapi.service.VeiculoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/veiculosExamesPraticos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API da relação de Veículos com Exames práticos")

public class VeiculoExamePraticoController {
    private final VeiculoExamePraticoService service;
    private final ExamePraticoService examePraticoService;
    private final VeiculoService veiculoService;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os Veículos relacionados a Exames práticos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<VeiculoExamePratico> veiculoExamePraticos = service.getVeiculoExamePratico();
        return ResponseEntity.ok(veiculoExamePraticos.stream().map(VeiculoExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Veículo e um Exame Prático que ele participa ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo/Exame Prático encontrado"),
            @ApiResponse(code = 404, message = "Veículo/Exame Prático não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<VeiculoExamePratico> veiculoExamePratico = service.getVeiculoExamePraticoById(id);
        if(!veiculoExamePratico.isPresent()) {
            return new ResponseEntity("Veículo ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(veiculoExamePratico.map(VeiculoExamePraticoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva a participação de um Veículo no Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo/Exame Prático salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Veículo/Exame Prático")
    })
    public ResponseEntity post(@RequestBody VeiculoExamePraticoDTO dto) {
        try {
            VeiculoExamePratico veiculoExamePratico = converter(dto);
            veiculoExamePratico = service.salvar(veiculoExamePratico);
            return new ResponseEntity(veiculoExamePratico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita a participação de um Veículo em um Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo/Exame Prático editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar Veículo/Exame Prático")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody VeiculoExamePraticoDTO dto) {
        if (!service.getVeiculoExamePraticoById(id).isPresent()) {
            return new ResponseEntity("Veículo ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            VeiculoExamePratico veiculoExamePratico = converter(dto);
            veiculoExamePratico.setId(id);
            service.salvar(veiculoExamePratico);
            return ResponseEntity.ok(veiculoExamePratico);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta a participação de um Veículo em um Exame Prático ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo/Exame Prático deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar Veículo/Exame Prático")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<VeiculoExamePratico> veiculoExamePratico = service.getVeiculoExamePraticoById(id);
        if (!veiculoExamePratico.isPresent()) {
            return new ResponseEntity("Veículo ou Exame Prático não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(veiculoExamePratico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public VeiculoExamePratico converter(VeiculoExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoExamePratico veiculoExamePratico = modelMapper.map(dto, VeiculoExamePratico.class);
        if (dto.getIdExamePratico() != null) {
            Optional<ExamePratico> examePratico = examePraticoService.getExamePraticoById(dto.getIdExamePratico());
            if (!examePratico.isPresent()) {
                veiculoExamePratico.setExamePratico(null);
            } else {
                veiculoExamePratico.setExamePratico(examePratico.get());
            }
        }
        if (dto.getIdVeiculo() != null) {
            Optional<Veiculo> veiculo = veiculoService.getVeiculoById(dto.getIdVeiculo());
            if (!veiculo.isPresent()) {
                veiculoExamePratico.setVeiculo(null);
            } else {
                veiculoExamePratico.setVeiculo(veiculo.get());
            }
        }
        return veiculoExamePratico;
    }
}

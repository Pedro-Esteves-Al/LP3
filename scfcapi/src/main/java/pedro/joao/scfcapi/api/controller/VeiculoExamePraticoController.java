package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.VeiculoDTO;
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

public class VeiculoExamePraticoController {
    private final VeiculoExamePraticoService service;
    private final ExamePraticoService examePraticoService;
    private final VeiculoService veiculoService;

    @GetMapping()
    public ResponseEntity get() {
        List<VeiculoExamePratico> veiculoExamePraticos = service.getVeiculoExamePratico();
        return ResponseEntity.ok(veiculoExamePraticos.stream().map(VeiculoExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<VeiculoExamePratico> veiculoExamePratico = service.getVeiculoExamePraticoById(id);
        if(!veiculoExamePratico.isPresent()) {
            return new ResponseEntity("Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(veiculoExamePratico.map(VeiculoExamePraticoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VeiculoExamePraticoDTO dto) {
        try {
            VeiculoExamePratico veiculoExamePratico = converter(dto);
            veiculoExamePratico = service.salvar(veiculoExamePratico);
            return new ResponseEntity(veiculoExamePratico,HttpStatus.CREATED);
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

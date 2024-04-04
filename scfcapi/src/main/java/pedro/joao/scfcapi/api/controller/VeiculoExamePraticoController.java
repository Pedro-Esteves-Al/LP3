package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.VeiculoExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.model.entity.VeiculoExamePratico;
import pedro.joao.scfcapi.service.ExamePraticoService;
import pedro.joao.scfcapi.service.VeiculoExamePraticoService;
import pedro.joao.scfcapi.service.VeiculoService;

import java.util.Optional;

@RestController
//@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
@CrossOrigin

public class VeiculoExamePraticoController {
    private final VeiculoExamePraticoService service;
    private final ExamePraticoService examePraticoService;
    private final VeiculoService veiculoService;

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

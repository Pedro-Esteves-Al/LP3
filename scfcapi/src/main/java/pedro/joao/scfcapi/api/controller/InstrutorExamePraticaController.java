package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.InstrutorExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.InstrutorExamePratico;
import pedro.joao.scfcapi.service.ExamePraticoService;
import pedro.joao.scfcapi.service.InstrutorExamePraticoService;
import pedro.joao.scfcapi.service.InstrutorService;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
public class InstrutorExamePraticaController {
    private final InstrutorExamePraticoService service;
    private final InstrutorService instrutorService;
    private final ExamePraticoService examePraticoService;

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

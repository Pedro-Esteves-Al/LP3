package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.exception.RegraNegocioException;

import pedro.joao.scfcapi.api.dto.AlunoExamePraticoDTO;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AlunoExamePratico;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.service.AlunoExamePraticoService;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.ExamePraticoService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
public class AlunoExamePraticoController {
    public final AlunoExamePraticoService service;
    public final ExamePraticoService examePraticoService;
    public final AlunoService alunoService;

    public AlunoExamePratico converter(AlunoExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoExamePratico alunoExamePratico = modelMapper.map(dto, AlunoExamePratico.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoExamePratico.setAluno(null);
            } else {
                alunoExamePratico.setAluno(aluno.get());
            }
        }
        if (dto.getIdExamePratico() != null) {
            Optional<ExamePratico> examePratico = examePraticoService.getExamePraticoById(dto.getIdExamePratico());
            if (!examePratico.isPresent()) {
                alunoExamePratico.setExamePratico(null);
            } else {
                alunoExamePratico.setExamePratico(examePratico.get());
            }
        }
        return alunoExamePratico;
    }
}

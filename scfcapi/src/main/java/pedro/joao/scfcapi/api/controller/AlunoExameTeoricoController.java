package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.AlunoAulaTeoricaDTO;
import pedro.joao.scfcapi.api.dto.AlunoExameTeoricoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.entity.AlunoExameTeorico;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.entity.ExameTeorico;
import pedro.joao.scfcapi.service.AlunoExameTeoricoService;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.ExameTeoricoService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoExameTeoricoController {
    private final AlunoExameTeoricoService service;
    private final AlunoService alunoService;
    private final ExameTeoricoService exameTeoricoService;

    public AlunoExameTeorico converter(AlunoExameTeoricoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoExameTeorico alunoExameTeorico = modelMapper.map(dto, AlunoExameTeorico.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoExameTeorico.setAluno(null);
            } else {
                alunoExameTeorico.setAluno(aluno.get());
            }
        }
        if (dto.getIdExameTeorico() != null) {
            Optional<ExameTeorico> exameTeorico = exameTeoricoService.getExameTeoricoById(dto.getIdExameTeorico());
            if (!exameTeorico.isPresent()) {
                alunoExameTeorico.setExameTeorico(null);
            } else {
                alunoExameTeorico.setExameTeorico(exameTeorico.get());
            }
        }
        return alunoExameTeorico;
    }
}

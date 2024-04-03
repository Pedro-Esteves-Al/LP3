package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import java.util.Optional;

import pedro.joao.scfcapi.api.dto.AlunoAulaTeoricaDTO;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.service.AlunoAulaTeoricaService;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.AulaTeoricaService;

@RestController
@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoAulaTeoricaController {
    private final AlunoAulaTeoricaService service;
    private final AlunoService alunoService;
    private final AulaTeoricaService aulaTeoricaService;

    public AlunoAulaTeorica converter(AlunoAulaTeoricaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoAulaTeorica alunoAulaTeorica = modelMapper.map(dto, AlunoAulaTeorica.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoAulaTeorica.setAluno(null);
            } else {
                alunoAulaTeorica.setAluno(aluno.get());
            }
        }
        if (dto.getIdAulaTeorica() != null) {
            Optional<AulaTeorica> aulaTeorica = aulaTeoricaService.getAulaTeoricaById(dto.getIdAulaTeorica());
            if (!aulaTeorica.isPresent()) {
                alunoAulaTeorica.setAulaTeorica(null);
            } else {
                alunoAulaTeorica.setAulaTeorica(aulaTeorica.get());
            }
        }
        return alunoAulaTeorica;
    }
}

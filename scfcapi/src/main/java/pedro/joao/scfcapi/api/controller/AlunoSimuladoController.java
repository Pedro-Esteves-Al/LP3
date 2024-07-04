package pedro.joao.scfcapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pedro.joao.scfcapi.api.dto.AlunoAulaTeoricaDTO;
import pedro.joao.scfcapi.api.dto.AlunoSimuladoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AlunoAulaTeorica;
import pedro.joao.scfcapi.model.entity.AlunoSimulado;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.entity.Simulado;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.AlunoSimuladoService;
import pedro.joao.scfcapi.service.SimuladoService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunosSimulados")
@RequiredArgsConstructor
@CrossOrigin

public class AlunoSimuladoController {
    private final AlunoSimuladoService service;
    private final AlunoService alunoService;
    private final SimuladoService simuladoService;

    public AlunoSimulado converter(AlunoSimuladoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoSimulado alunoSimulado = modelMapper.map(dto, AlunoSimulado.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                alunoSimulado.setAluno(null);
            } else {
                alunoSimulado.setAluno(aluno.get());
            }
        }
        if (dto.getIdSimulado() != null) {
            Optional<Simulado> simulado = simuladoService.getSimuladoById(dto.getIdSimulado());
            if (!simulado.isPresent()) {
                alunoSimulado.setSimulado(null);
            } else {
                alunoSimulado.setSimulado(simulado.get());
            }
        }
        return alunoSimulado;
    }
}

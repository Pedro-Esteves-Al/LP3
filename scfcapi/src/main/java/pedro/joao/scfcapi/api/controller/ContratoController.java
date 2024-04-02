package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ContratoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.Contrato;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.ContratoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Contratos")
@RequiredArgsConstructor

public class ContratoController {
    private final ContratoService service;
    private final AlunoService alunoService;

    public Contrato converter(ContratoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Contrato contrato = modelMapper.map(dto, Contrato.class);
         if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                contrato.setAluno(null);
            } else {
                contrato.setAluno(aluno.get());
            }
        }
        return contrato;
    }
}

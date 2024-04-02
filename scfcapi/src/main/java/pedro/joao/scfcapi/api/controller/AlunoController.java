package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AlunoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.joao.scfcapi.service.AlunoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Alunos")
@RequiredArgsConstructor

public class AlunoController {

    private final AlunoService service;

    public Aluno converter(AlunoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = modelMapper.map(dto, Aluno.class);
        return aluno;
    }
}

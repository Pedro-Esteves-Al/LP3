package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AulaPraticaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.AulaPratica;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.AulaPraticaService;
import pedro.joao.scfcapi.service.InstrutorService;
import pedro.joao.scfcapi.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/AulasPraticas")
@RequiredArgsConstructor

public class AulaPraticaController {
    private final AulaPraticaService service;
    private final AlunoService alunoService;
    private final InstrutorService instrutorService;
    private final VeiculoService veiculoService;

    public AulaPratica converter(AulaPraticaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AulaPratica aulaPratica = modelMapper.map(dto, AulaPratica.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                aulaPratica.setAluno(null);
            } else {
                aulaPratica.setAluno(aluno.get());
            }
        }
        if (dto.getIdVeiculo() != null) {
            Optional<Veiculo> veiculo = veiculoService.getVeiculoById(dto.getIdVeiculo());
            if (!veiculo.isPresent()) {
                aulaPratica.setVeiculo(null);
            } else {
                aulaPratica.setVeiculo(veiculo.get());
            }
        }
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> instrutor = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!instrutor.isPresent()) {
                aulaPratica.setInstrutor(null);
            } else {
                aulaPratica.setInstrutor(instrutor.get());
            }
        }
        return aulaPratica;
    }
}

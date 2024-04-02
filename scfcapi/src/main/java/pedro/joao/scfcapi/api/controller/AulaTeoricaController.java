package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AulaTeoricaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.service.AulaTeoricaService;
import pedro.joao.scfcapi.service.InstrutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/AulasTeoricas")
@RequiredArgsConstructor

public class AulaTeoricaController {
    private final AulaTeoricaService service;
    private final InstrutorService instrutorService;

    public AulaTeorica converter(AulaTeoricaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AulaTeorica aulaTeorica = modelMapper.map(dto, AulaTeorica.class);
        if (dto.getIdInstrutor() != null) {
            Optional<Instrutor> instrutor = instrutorService.getInstrutorById(dto.getIdInstrutor());
            if (!instrutor.isPresent()) {
                aulaTeorica.setInstrutor(null);
            } else {
                aulaTeorica.setInstrutor(instrutor.get());
            }
        }
        return aulaTeorica;
    }
}

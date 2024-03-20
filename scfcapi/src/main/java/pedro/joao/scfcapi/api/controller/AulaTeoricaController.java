package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AulaTeoricaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaTeorica;
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

    public AulaTeorica converter(AulaTeoricaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, AulaTeorica.class);
    }
}

package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AulaPraticaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaPratica;
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

    public AulaPratica converter(AulaPraticaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, AulaPratica.class);
    }
}

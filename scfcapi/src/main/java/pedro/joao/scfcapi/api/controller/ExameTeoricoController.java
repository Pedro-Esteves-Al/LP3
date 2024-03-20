package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ExameTeoricoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.ExameTeorico;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ExamesTeoricos")
@RequiredArgsConstructor

public class ExameTeoricoController {

    public ExameTeorico converter(ExameTeoricoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, ExameTeorico.class);
    }
}

package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.InstrutorDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Instrutor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Instrutores")
@RequiredArgsConstructor

public class InstrutorController {

    public Instrutor converter(InstrutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Instrutor instrutor = modelMapper.map(dto, Instrutor.class);
        return instrutor;
    }
}

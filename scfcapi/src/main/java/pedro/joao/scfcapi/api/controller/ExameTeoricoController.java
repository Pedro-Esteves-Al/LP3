package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ExameTeoricoDTO;
import pedro.joao.scfcapi.api.dto.InstrutorDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.ExameTeorico;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.ExameTeoricoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/examesTeoricos")
@RequiredArgsConstructor
@CrossOrigin

public class ExameTeoricoController {
    private final ExameTeoricoService service;
    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity get() {
        List<ExameTeorico> examesTeoricos = service.getExameTeoricos();
        return ResponseEntity.ok(examesTeoricos.stream().map(ExameTeoricoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<ExameTeorico> exameTeorico = service.getExameTeoricoById(id);
        if(!exameTeorico.isPresent()) {
            return new ResponseEntity("ExameTeorico não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exameTeorico.map(ExameTeoricoDTO::create));
    }

    public ExameTeorico converter(ExameTeoricoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ExameTeorico exameTeorico = modelMapper.map(dto, ExameTeorico.class);
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                exameTeorico.setCategoria(null);
            } else {
                exameTeorico.setCategoria(cateogria.get());
            }
        }
        return exameTeorico;
    }
}

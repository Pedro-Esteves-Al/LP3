package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.ExamePraticoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.ExamePratico;
import pedro.joao.scfcapi.service.CategoriaService;
import pedro.joao.scfcapi.service.ExamePraticoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/examesPraticos")
@RequiredArgsConstructor
@CrossOrigin

public class ExamePraticoController {
    public final ExamePraticoService service;
    public final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity get() {
        List<ExamePratico> examePraticos = service.getExamePraticos();
        return ResponseEntity.ok(examePraticos.stream().map(ExamePraticoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<ExamePratico> examePratico = service.getExamePraticoById(id);
        if(!examePratico.isPresent()) {
            return new ResponseEntity("ExamePratico não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(examePratico.map(ExamePraticoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ExamePraticoDTO dto) {
        try {
            ExamePratico examePratico = converter(dto);
            examePratico = service.salvar(examePratico);
            return new ResponseEntity(examePratico,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    public ExamePratico converter(ExamePraticoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ExamePratico examePratico = modelMapper.map(dto, ExamePratico.class);
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                examePratico.setCategoria(null);
            } else {
                examePratico.setCategoria(cateogria.get());
            }
        }
        return examePratico;
    }
}

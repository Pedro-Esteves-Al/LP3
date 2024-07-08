package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.CategoriaDTO;
import pedro.joao.scfcapi.api.dto.ContratoDTO;
import pedro.joao.scfcapi.api.dto.ContratoDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.entity.Contrato;
import pedro.joao.scfcapi.model.entity.Contrato;
import pedro.joao.scfcapi.service.AlunoService;
import pedro.joao.scfcapi.service.CategoriaService;
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
@RequestMapping("/api/v1/contratos")
@RequiredArgsConstructor
@CrossOrigin

public class ContratoController {
    private final ContratoService service;
    private final AlunoService alunoService;
    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity get() {
        List<Contrato> contratos = service.getContratos();
        return ResponseEntity.ok(contratos.stream().map(ContratoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Contrato> contrato = service.getContratoById(id);
        if(!contrato.isPresent()) {
            return new ResponseEntity("Contrato não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(contrato.map(ContratoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ContratoDTO dto) {
        try {
            Contrato contrato = converter(dto);
            contrato = service.salvar(contrato);
            return new ResponseEntity(contrato,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cateogria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cateogria.isPresent()) {
                contrato.setCategoria(null);
            } else {
                contrato.setCategoria(cateogria.get());
            }
        }
        return contrato;
    }
}

package pedro.joao.scfcapi.api.controller;

import pedro.joao.scfcapi.api.dto.AulaPraticaDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.*;
import pedro.joao.scfcapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aulasPraticas")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Cursos")


public class AulaPraticaController {
    private final AulaPraticaService service;
    private final AlunoService alunoService;
    private final InstrutorService instrutorService;
    private final VeiculoService veiculoService;
    private final CategoriaService categoriaService;

    @GetMapping()
    @ApiOperation("Obter a lista de todas as Aulas Práticas cadastradas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<AulaPratica> aulaPraticas = service.getAulaPraticas();
        return ResponseEntity.ok(aulaPraticas.stream().map(AulaPraticaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Aula Prática")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aula Prática encontrada"),
            @ApiResponse(code = 404, message = "Aula Prática não encontrada")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<AulaPratica> aulaPratica = service.getAulaPraticaById(id);
        if(!aulaPratica.isPresent()) {
            return new ResponseEntity("Aula prática não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(aulaPratica.map(AulaPraticaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Aula Prática")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aula Prática salva com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar a Aula Prática")
    })
    public ResponseEntity post(@RequestBody AulaPraticaDTO dto) {
        try {
            AulaPratica aulaPratica = converter(dto);
            aulaPratica = service.salvar(aulaPratica);
            return new ResponseEntity(aulaPratica,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita as informações de uma Aula Prática")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aula Prática editada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar a Aula Prática")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AulaPraticaDTO dto) {
        if (!service.getAulaPraticaById(id).isPresent()) {
            return new ResponseEntity("Aula prática não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            AulaPratica aulaPratica = converter(dto);
            aulaPratica.setId(id);
            service.salvar(aulaPratica);
            return ResponseEntity.ok(aulaPratica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma Aula Prática")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aula Prática deletada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar a Aula Prática")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AulaPratica> aulaPratica = service.getAulaPraticaById(id);
        if (!aulaPratica.isPresent()) {
            return new ResponseEntity("Aula Prática não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(aulaPratica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!categoria.isPresent()) {
                aulaPratica.setCategoria(null);
            } else {
                aulaPratica.setCategoria(categoria.get());
            }
        }
        return aulaPratica;
    }
}

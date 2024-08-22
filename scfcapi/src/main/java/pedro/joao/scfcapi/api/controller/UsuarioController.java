package pedro.joao.scfcapi.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pedro.joao.scfcapi.api.dto.UsuarioDTO;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Usuario;
import pedro.joao.scfcapi.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Usuarios")

public class UsuarioController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;

    @GetMapping()
    @ApiOperation("Obter a lista de todos os usuários cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista encontrada"),
            @ApiResponse(code = 404, message = "Lista não encontrada")
    })
    public ResponseEntity get() {
        List<Usuario> usuarios = service.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário encontrado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if(!usuario.isPresent()) {
            return new ResponseEntity("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario.map(UsuarioDTO::create));
    }

    /*@PostMapping()
    @ApiOperation("Salva um novo Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário salvo com sucesso"),
            @ApiResponse(code = 404, message = "erro ao salvar o Usuário")
    })
    public ResponseEntity post(@RequestBody UsuarioDTO dto) {
        try {
            Usuario usuario = converter(dto);
            usuario = service.salvar(usuario);
            return new ResponseEntity(usuario,HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    @PostMapping()
    public ResponseEntity post(@RequestBody UsuarioDTO dto) {
        try {
            if (dto.getSenha() == null || dto.getSenha().trim().equals("") ||
                    dto.getSenhaRepeticao() == null || dto.getSenhaRepeticao().trim().equals("")) {
                return ResponseEntity.badRequest().body("Senha inválida");
            }
            if (!dto.getSenha().equals(dto.getSenhaRepeticao())) {
                return ResponseEntity.badRequest().body("Senhas não conferem");
            }
            Usuario usuario = converter(dto);
            String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
            usuario.setSenha(senhaCriptografada);
            usuario = service.salvar(usuario);
            return new ResponseEntity(usuario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*@PutMapping("{id}")
    @ApiOperation("Edita as informações de um Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário editado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao editar o Usuário")
    })
    public ResponseEntity atualizar (@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        if (!service.getUsuarioById(id).isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Usuario usuario = converter(dto);
            usuario.setId(id);
            service.salvar(usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        if (!service.getUsuarioById(id).isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            if (dto.getSenha() == null || dto.getSenha().trim().equals("") ||
                    dto.getSenhaRepeticao() == null || dto.getSenhaRepeticao().trim().equals("")) {
                return ResponseEntity.badRequest().body("Senha inválida");
            }
            if (!dto.getSenha().equals(dto.getSenhaRepeticao())) {
                return ResponseEntity.badRequest().body("Senhas não conferem");
            }
            Usuario usuario = converter(dto);
            usuario.setId(id);
            service.salvar(usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    @ApiOperation("Deleta um Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao deletar o Usuário")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(usuario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Usuario converter(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        return usuario;
    }
}

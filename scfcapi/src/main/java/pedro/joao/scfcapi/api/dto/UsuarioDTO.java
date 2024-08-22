package pedro.joao.scfcapi.api.dto;

import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Usuario;

public class UsuarioDTO {
    private Long id;
    private String login;
    private String cpf;
    private String senha;
    private boolean admin;

    public static UsuarioDTO create(Usuario usuario) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return dto;
    }
}

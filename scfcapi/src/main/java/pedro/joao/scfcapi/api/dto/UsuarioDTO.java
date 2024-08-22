package pedro.joao.scfcapi.api.dto;

import org.modelmapper.ModelMapper;
import pedro.joao.scfcapi.model.entity.Usuario;

public class UsuarioDTO {
    private Long id;
    private String login;
    private String cpf;
    private String senhaRepeticao;
    private String senha;
    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenhaRepeticao() {
        return senhaRepeticao;
    }

    public void setSenhaRepeticao(String senhaRepeticao) {
        this.senhaRepeticao = senhaRepeticao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public static UsuarioDTO create(Usuario usuario) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return dto;
    }
}

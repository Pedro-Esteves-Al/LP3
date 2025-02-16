package pedro.joao.scfcapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.repository.AlunoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setNome("Aluno Teste");
        aluno.setEmail("teste@email.com");
        aluno.setTelefone("123456789");
        aluno.setCpf("123.456.789-00");
        aluno.setLogradouro("Rua Teste");
        aluno.setNumero("123");
        aluno.setBairro("Bairro Teste");
        aluno.setCidade("Cidade Teste");
        aluno.setUf("UF");
        aluno.setCep("12345-678");
        aluno.setMatricula("123456");
    }

    @Test
    void deveRetornarListaDeAlunos() {
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno));
        List<Aluno> alunos = alunoService.getAlunos();
        assertFalse(alunos.isEmpty());
        assertEquals(1, alunos.size());
    }

    @Test
    void deveRetornarAlunoPorId() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        Optional<Aluno> resultado = alunoService.getAlunoById(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Aluno Teste", resultado.get().getNome());
    }

    @Test
    void deveSalvarAluno() {
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);
        Aluno salvo = alunoService.salvar(aluno);
        assertNotNull(salvo);
        assertEquals("Aluno Teste", salvo.getNome());
    }

    @Test
    void naoDeveSalvarAlunoComNomeNulo() {
        aluno.setNome(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComNomeVazio() {
        aluno.setNome(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComEmailNulo() {
        aluno.setEmail(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComEmailVazio() {
        aluno.setEmail(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComTelefoneNulo() {
        aluno.setTelefone(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComTelefoneVazio() {
        aluno.setTelefone(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComCpfNulo() {
        aluno.setCpf(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComCpfVazio() {
        aluno.setCpf(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComLogradouroNulo() {
        aluno.setLogradouro(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComLogradouroVazio() {
        aluno.setLogradouro(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComNumeroNulo() {
        aluno.setNumero(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComNumeroVazio() {
        aluno.setNumero(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComBairroNulo() {
        aluno.setBairro(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComBairroVazio() {
        aluno.setBairro(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComCidadeNula() {
        aluno.setCidade(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComCidadeVazia() {
        aluno.setCidade(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComUfNula() {
        aluno.setUf(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComUfVazia() {
        aluno.setUf(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComCepNulo() {
        aluno.setCep(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComCepVazio() {
        aluno.setCep(" ");
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void naoDeveSalvarAlunoComMatriculaNula() {
        aluno.setMatricula(null);
        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
    }

    @Test
    void deveExcluirAlunoComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setId(1L); // Necessário para a exclusão funcionar

        //o doNothing é pra evitar conflitos entre o mock e o resto do negócio.
        doNothing().when(alunoRepository).delete(aluno);

        assertDoesNotThrow(() -> alunoService.excluir(aluno));

        verify(alunoRepository, times(1)).delete(aluno);
    }

    @Test
    void naoDeveExcluirAlunoSemId() {
        Aluno aluno = new Aluno();
        aluno.setId(null); // ID nulo deve gerar erro

        assertThrows(NullPointerException.class, () -> alunoService.excluir(aluno));

        verify(alunoRepository, never()).delete(any());
    }
}
//package pedro.joao.scfcapi.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import pedro.joao.scfcapi.exception.RegraNegocioException;
//import pedro.joao.scfcapi.model.entity.Aluno;
//import pedro.joao.scfcapi.model.repository.AlunoRepository;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//class AlunoServiceTest {
//
//    @Mock
//    private AlunoRepository repository;
//
//    @InjectMocks
//    private AlunoService alunoService;
//
//    private Aluno aluno;
//
//    @BeforeEach
//    void setUp() {
//        aluno = new Aluno();
//        aluno.setId(1L);
//        aluno.setNome("João Silva");
//        aluno.setEmail("joao@example.com");
//        aluno.setTelefone("11999999999");
//        aluno.setCpf("123.456.789-00");
//        aluno.setLogradouro("Rua A");
//        aluno.setNumero("100");
//        aluno.setBairro("Centro");
//        aluno.setCidade("São Paulo");
//        aluno.setUf("SP");
//        aluno.setCep("01000-000");
//        aluno.setMatricula("2025001");
//    }
//
//    @Test
//    void deveRetornarListaDeAlunos() {
//        when(repository.findAll()).thenReturn(Arrays.asList(aluno));
//        List<Aluno> alunos = alunoService.getAlunos();
//        assertFalse(alunos.isEmpty());
//        assertEquals(1, alunos.size());
//    }
//
//    @Test
//    void deveRetornarAlunoPorId() {
//        when(repository.findById(1L)).thenReturn(Optional.of(aluno));
//        Optional<Aluno> resultado = alunoService.getAlunoById(1L);
//        assertTrue(resultado.isPresent());
//        assertEquals("João Silva", resultado.get().getNome());
//    }
//
//    @Test
//    void deveSalvarAluno() {
//        when(repository.save(any(Aluno.class))).thenReturn(aluno);
//        Aluno salvo = alunoService.salvar(aluno);
//        assertNotNull(salvo);
//        assertEquals("João Silva", salvo.getNome());
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComNomeInvalido() {
//        aluno.setNome(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComEmailInvalido() {
//        aluno.setEmail(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComTelefoneInvalido() {
//        aluno.setTelefone(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComCpfInvalido() {
//        aluno.setCpf(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComLogradouroInvalido() {
//        aluno.setLogradouro(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComNumeroInvalido() {
//        aluno.setNumero(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComBairroInvalido() {
//        aluno.setBairro(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComCidadeInvalida() {
//        aluno.setCidade(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComUfInvalida() {
//        aluno.setUf(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComCepInvalido() {
//        aluno.setCep(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void naoDeveSalvarAlunoComMatriculaInvalida() {
//        aluno.setMatricula(null);
//        assertThrows(RegraNegocioException.class, () -> alunoService.salvar(aluno));
//    }
//
//    @Test
//    void deveExcluirAluno() {
//        aluno.setId(1L);
//        doNothing().when(repository).delete(aluno);
//        assertDoesNotThrow(() -> alunoService.excluir(aluno));
//        verify(repository, times(1)).delete(aluno);
//    }
//}

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
package pedro.joao.scfcapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.repository.InstrutorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrutorServiceTest {

    @InjectMocks
    private InstrutorService instrutorService;

    @Mock
    private InstrutorRepository instrutorRepository;

    private Instrutor instrutor;

    @BeforeEach
    void setUp() {
        instrutor = new Instrutor();
        instrutor = new Instrutor();
        instrutor.setNome("Instrutor Teste");
        instrutor.setEmail("teste@email.com");
        instrutor.setTelefone("123456789");
        instrutor.setCpf("123.456.789-00");
        instrutor.setLogradouro("Rua Teste");
        instrutor.setNumero("123");
        instrutor.setBairro("Bairro Teste");
        instrutor.setCidade("Cidade Teste");
        instrutor.setUf("UF");
        instrutor.setCep("12345-678");
        instrutor.setClt("123456");
    }

    @Test
    void deveRetornarListaDeInstrutores() {
        when(instrutorRepository.findAll()).thenReturn(Arrays.asList(instrutor));
        List<Instrutor> instrutores = instrutorService.getInstrutors();
        assertFalse(instrutores.isEmpty());
        assertEquals(1, instrutores.size());
    }

    @Test
    void deveRetornarInstrutorPorId() {

        when(instrutorRepository.findById(1L)).thenReturn(Optional.of(instrutor));
        Optional<Instrutor> resultado = instrutorService.getInstrutorById(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Instrutor Teste", resultado.get().getNome());
    }

    @Test
    void deveSalvarInstrutor() {

        when(instrutorRepository.save(any(Instrutor.class))).thenReturn(instrutor);
        Instrutor salvo = instrutorService.salvar(instrutor);
        assertNotNull(salvo);
        assertEquals("Instrutor Teste", salvo.getNome());
    }

    @Test
    void deveExcluirInstrutorComSucesso() {
        Instrutor instrutor = new Instrutor();
        instrutor.setId(1L); // Necessário para a exclusão funcionar

        //o doNothing é usado para evitar conflitos entre o mock e a aplicação.
        doNothing().when(instrutorRepository).delete(instrutor);

        assertDoesNotThrow(() -> instrutorService.excluir(instrutor));

        verify(instrutorRepository, times(1)).delete(instrutor);
    }

    @Test
    void naoDeveExcluirInstrutorSemId() {
        Instrutor instrutor = new Instrutor();
        instrutor.setId(null); // ID nulo deve gerar erro

        assertThrows(NullPointerException.class, () -> instrutorService.excluir(instrutor));

        verify(instrutorRepository, never()).delete(any());
    }

    @Test
    void naoDeveSalvarInstrutorComNomeNulo() {
        instrutor.setNome(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComNomeVazio() {
        instrutor.setNome(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComEmailNulo() {
        instrutor.setEmail(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComEmailVazio() {
        instrutor.setEmail(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComTelefoneNulo() {
        instrutor.setTelefone(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComTelefoneVazio() {
        instrutor.setTelefone(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCpfNulo() {
        instrutor.setCpf(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCpfVazio() {
        instrutor.setCpf(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComLogradouroNulo() {
        instrutor.setLogradouro(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComLogradouroVazio() {
        instrutor.setLogradouro(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComNumeroNulo() {
        instrutor.setNumero(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComNumeroVazio() {
        instrutor.setNumero(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComBairroNulo() {
        instrutor.setBairro(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComBairroVazio() {
        instrutor.setBairro(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCidadeNula() {
        instrutor.setCidade(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCidadeVazia() {
        instrutor.setCidade(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComUfNula() {
        instrutor.setUf(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComUfVazia() {
        instrutor.setUf(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCepNulo() {
        instrutor.setCep(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCepVazio() {
        instrutor.setCep(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCltNulo() {
        instrutor.setClt(null);
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }

    @Test
    void naoDeveSalvarInstrutorComCltVazio() {
        instrutor.setClt(" ");
        assertThrows(RegraNegocioException.class, () -> instrutorService.salvar(instrutor));
    }
}

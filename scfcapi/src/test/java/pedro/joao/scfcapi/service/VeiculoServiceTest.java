package pedro.joao.scfcapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.model.repository.VeiculoRepository;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.repository.InstrutorRepository;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.repository.CategoriaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo();
        veiculo.setModelo("Modelo Teste");
        veiculo.setPlaca("ABC-1234");
        veiculo.setInstrutor(new Instrutor()); // Simula um instrutor válido
        veiculo.setCategoria(new Categoria());// Simula uma categoria válido
    }

    @Test
    void deveRetornarListaDeVeiculos() {
        when(veiculoRepository.findAll()).thenReturn(Arrays.asList(veiculo));
        List<Veiculo> veiculos = veiculoService.getVeiculos();
        assertFalse(veiculos.isEmpty());
        assertEquals(1, veiculos.size());
    }

    @Test
    void deveRetornarVeiculoPorId() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        Optional<Veiculo> resultado = veiculoService.getVeiculoById(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Modelo Teste", resultado.get().getModelo());
    }

    @Test
    void deveSalvarVeiculo() {
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);
        Veiculo salvo = veiculoService.salvar(veiculo);
        assertNotNull(salvo);
        assertEquals("Modelo Teste", salvo.getModelo());
    }

    @Test
    void naoDeveSalvarVeiculoComInstrutorNulo() {
        veiculo.setInstrutor(null);
        assertThrows(RegraNegocioException.class, () -> veiculoService.salvar(veiculo));
    }

    @Test
    void naoDeveSalvarVeiculoComCategoriaNula() {
        veiculo.setCategoria(null);
        assertThrows(RegraNegocioException.class, () -> veiculoService.salvar(veiculo));
    }

    @Test
    void naoDeveSalvarVeiculoComModeloNulo() {
        veiculo.setModelo(null);
        assertThrows(RegraNegocioException.class, () -> veiculoService.salvar(veiculo));
    }

    @Test
    void naoDeveSalvarVeiculoComModeloVazio() {
        veiculo.setModelo(" ");
        assertThrows(RegraNegocioException.class, () -> veiculoService.salvar(veiculo));
    }

    @Test
    void naoDeveSalvarVeiculoComPlacaNula() {
        veiculo.setPlaca(null);
        assertThrows(RegraNegocioException.class, () -> veiculoService.salvar(veiculo));
    }

    @Test
    void naoDeveSalvarVeiculoComPlacaVazia() {
        veiculo.setPlaca(" ");
        assertThrows(RegraNegocioException.class, () -> veiculoService.salvar(veiculo));
    }

    @Test
    void deveExcluirVeiculoComSucesso() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L); // Necessário para a exclusão funcionar

        //o doNothing é pra evitar conflitos entre o mock e o resto do negócio.
        doNothing().when(veiculoRepository).delete(veiculo);

        assertDoesNotThrow(() -> veiculoService.excluir(veiculo));

        verify(veiculoRepository, times(1)).delete(veiculo);
    }

    @Test
    void naoDeveExcluirVeiculoSemId() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(null); // ID nulo deve gerar erro

        assertThrows(NullPointerException.class, () -> veiculoService.excluir(veiculo));

        verify(veiculoRepository, never()).delete(any());
    }
}
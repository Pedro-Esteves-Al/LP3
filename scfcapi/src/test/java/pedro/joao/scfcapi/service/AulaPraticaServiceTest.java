package pedro.joao.scfcapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.joao.scfcapi.exception.RegraNegocioException;
import pedro.joao.scfcapi.model.entity.AulaPratica;
import pedro.joao.scfcapi.model.entity.Aluno;
import pedro.joao.scfcapi.model.entity.Instrutor;
import pedro.joao.scfcapi.model.entity.Veiculo;
import pedro.joao.scfcapi.model.entity.Categoria;
import pedro.joao.scfcapi.model.repository.AulaPraticaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AulaPraticaServiceTest {

    @InjectMocks
    private AulaPraticaService aulaPraticaService;

    @Mock
    private AulaPraticaRepository aulaPraticaRepository;

    private AulaPratica aulaPratica;

    @BeforeEach
    void setUp() {
        aulaPratica = new AulaPratica();
        aulaPratica.setAluno(new Aluno()); // Simula um aluno válido
        aulaPratica.setInstrutor(new Instrutor()); // Simula um instrutor válido
        aulaPratica.setVeiculo(new Veiculo()); // Simula um veículo válido
        aulaPratica.setDataAulaPratica(LocalDate.now());
        aulaPratica.setHorarioAulaPratica(LocalTime.now());
        aulaPratica.setCategoria(new Categoria()); // Simula uma categoria válida
    }

    @Test
    void deveRetornarListaDeAulaPraticas() {
        when(aulaPraticaRepository.findAll()).thenReturn(Arrays.asList(aulaPratica));
        List<AulaPratica> aulasPraticas = aulaPraticaService.getAulaPraticas();
        assertFalse(aulasPraticas.isEmpty());
        assertEquals(1, aulasPraticas.size());
    }

    @Test
    void deveRetornarAulaPraticaPorId() {
        when(aulaPraticaRepository.findById(1L)).thenReturn(Optional.of(aulaPratica));
        Optional<AulaPratica> resultado = aulaPraticaService.getAulaPraticaById(1L);
        assertTrue(resultado.isPresent());
        assertEquals(aulaPratica, resultado.get());
    }

    @Test
    void deveSalvarAulaPratica() {
        when(aulaPraticaRepository.save(any(AulaPratica.class))).thenReturn(aulaPratica);
        AulaPratica salvo = aulaPraticaService.salvar(aulaPratica);
        assertNotNull(salvo);
        assertEquals(aulaPratica, salvo);
    }

    @Test
    void naoDeveSalvarAulaPraticaComAlunoNulo() {
        aulaPratica.setAluno(null);
        assertThrows(RegraNegocioException.class, () -> aulaPraticaService.salvar(aulaPratica));
    }

    @Test
    void naoDeveSalvarAulaPraticaComInstrutorNulo() {
        aulaPratica.setInstrutor(null);
        assertThrows(RegraNegocioException.class, () -> aulaPraticaService.salvar(aulaPratica));
    }

    @Test
    void naoDeveSalvarAulaPraticaComVeiculoNulo() {
        aulaPratica.setVeiculo(null);
        assertThrows(RegraNegocioException.class, () -> aulaPraticaService.salvar(aulaPratica));
    }

    @Test
    void naoDeveSalvarAulaPraticaComDataNula() {
        aulaPratica.setDataAulaPratica(null);
        assertThrows(RegraNegocioException.class, () -> aulaPraticaService.salvar(aulaPratica));
    }

    @Test
    void naoDeveSalvarAulaPraticaComHorarioNulo() {
        aulaPratica.setHorarioAulaPratica(null);
        assertThrows(RegraNegocioException.class, () -> aulaPraticaService.salvar(aulaPratica));
    }

    @Test
    void naoDeveSalvarAulaPraticaComCategoriaNula() {
        aulaPratica.setCategoria(null);
        assertThrows(RegraNegocioException.class, () -> aulaPraticaService.salvar(aulaPratica));
    }

    @Test
    void deveExcluirAulaPraticaComSucesso() {
        AulaPratica aulaPratica = new AulaPratica();
        aulaPratica.setId(1L); // Necessário para a exclusão funcionar

        doNothing().when(aulaPraticaRepository).delete(aulaPratica);

        assertDoesNotThrow(() -> aulaPraticaService.excluir(aulaPratica));

        verify(aulaPraticaRepository, times(1)).delete(aulaPratica);
    }

    @Test
    void naoDeveExcluirAulaPraticaSemId() {
        AulaPratica aulaPratica = new AulaPratica();
        aulaPratica.setId(null); // ID nulo deve gerar erro

        assertThrows(NullPointerException.class, () -> aulaPraticaService.excluir(aulaPratica));

        verify(aulaPraticaRepository, never()).delete(any());
    }
}
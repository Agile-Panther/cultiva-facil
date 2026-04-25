package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotacaoCulturasServicoTest {

    @Mock
    private IntervalodeDescansoRepositorio repositorio;

    @InjectMocks
    private RotacaoCulturasServico servico;

    // F-08 RN-051 — sem historico encerrado, cadastro e rejeitado
    @Test
    void deveRejeitarCadastroSemHistoricoEncerrado() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Milho");

        when(repositorio.existeCicloEncerrado(talhaoId, cultura)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrarIntervalo(talhaoId, cultura, new DiasDescanso(20)));

        verify(repositorio, times(1)).existeCicloEncerrado(talhaoId, cultura);
    }

    // F-08 RN-051 (positivo)
    @Test
    void deveCadastrarIntervaloComHistoricoEncerrado() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");

        when(repositorio.existeCicloEncerrado(talhaoId, cultura)).thenReturn(true);

        assertDoesNotThrow(() -> servico.cadastrarIntervalo(talhaoId, cultura, new DiasDescanso(30)));

        verify(repositorio, times(1)).salvar(any(IntervalodeDescanso.class));
    }

    // F-08 RN-053 — colheita ha 20 dias, intervalo de 30: bloqueado
    @Test
    void deveBloquearVinculoDentroDoIntervalo() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");
        var intervalo = new IntervalodeDescanso(talhaoId, cultura,
                new DiasDescanso(30), LocalDate.now().minusDays(20));

        when(repositorio.buscarIntervalo(talhaoId, cultura)).thenReturn(Optional.of(intervalo));

        assertThrows(IllegalArgumentException.class,
                () -> servico.validarDescanso(talhaoId, cultura, LocalDate.now()));

        verify(repositorio, times(1)).buscarIntervalo(talhaoId, cultura);
    }

    // F-08 RN-053 (positivo) — colheita ha 35 dias, intervalo de 30: liberado
    @Test
    void devePermitirVinculoAposIntervalo() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");
        var intervalo = new IntervalodeDescanso(talhaoId, cultura,
                new DiasDescanso(30), LocalDate.now().minusDays(35));

        when(repositorio.buscarIntervalo(talhaoId, cultura)).thenReturn(Optional.of(intervalo));

        assertDoesNotThrow(() -> servico.validarDescanso(talhaoId, cultura, LocalDate.now()));
    }

    // Sem intervalo configurado — vinculo sempre liberado
    @Test
    void devePermitirVinculoSemIntervaloConfigurado() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Milho");

        when(repositorio.buscarIntervalo(talhaoId, cultura)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> servico.validarDescanso(talhaoId, cultura, LocalDate.now()));
    }
}

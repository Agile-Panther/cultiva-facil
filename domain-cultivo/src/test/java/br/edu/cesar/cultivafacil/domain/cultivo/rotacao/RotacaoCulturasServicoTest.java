package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;
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

    // F-08 RN-051 — sem histórico encerrado, cadastro é rejeitado
    @Test
    void deveRejeitarCadastroSemHistoricoEncerrado() {
        var zonaId = ZonaId.novo();
        var cultura = new NomeCultura("Milho");

        when(repositorio.existeCicloEncerrado(zonaId, cultura)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrarIntervalo(zonaId, cultura, new DiasDescanso(20)));

        verify(repositorio, times(1)).existeCicloEncerrado(zonaId, cultura);
    }

    // F-08 RN-051 (positivo)
    @Test
    void deveCadastrarIntervaloComHistoricoEncerrado() {
        var zonaId = ZonaId.novo();
        var cultura = new NomeCultura("Tomate");

        when(repositorio.existeCicloEncerrado(zonaId, cultura)).thenReturn(true);

        assertDoesNotThrow(() -> servico.cadastrarIntervalo(zonaId, cultura, new DiasDescanso(30)));

        verify(repositorio, times(1)).salvar(any(IntervalodeDescanso.class));
    }

    // F-08 RN-053 — colheita há 20 dias, intervalo de 30: bloqueado
    @Test
    void deveBloquearVinculoDentroDoIntervalo() {
        var zonaId = ZonaId.novo();
        var cultura = new NomeCultura("Tomate");
        var intervalo = new IntervalodeDescanso(zonaId, cultura,
                new DiasDescanso(30), LocalDate.now().minusDays(20));

        when(repositorio.buscarIntervalo(zonaId, cultura)).thenReturn(Optional.of(intervalo));

        assertThrows(IllegalArgumentException.class,
                () -> servico.validarDescanso(zonaId, cultura, LocalDate.now()));

        verify(repositorio, times(1)).buscarIntervalo(zonaId, cultura);
    }

    // F-08 RN-053 (positivo) — colheita há 35 dias, intervalo de 30: liberado
    @Test
    void devePermitirVinculoAposIntervalo() {
        var zonaId = ZonaId.novo();
        var cultura = new NomeCultura("Tomate");
        var intervalo = new IntervalodeDescanso(zonaId, cultura,
                new DiasDescanso(30), LocalDate.now().minusDays(35));

        when(repositorio.buscarIntervalo(zonaId, cultura)).thenReturn(Optional.of(intervalo));

        assertDoesNotThrow(() -> servico.validarDescanso(zonaId, cultura, LocalDate.now()));
    }

    // Sem intervalo configurado — vínculo sempre liberado
    @Test
    void devePermitirVinculoSemIntervaloConfigurado() {
        var zonaId = ZonaId.novo();
        var cultura = new NomeCultura("Milho");

        when(repositorio.buscarIntervalo(zonaId, cultura)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> servico.validarDescanso(zonaId, cultura, LocalDate.now()));
    }
}

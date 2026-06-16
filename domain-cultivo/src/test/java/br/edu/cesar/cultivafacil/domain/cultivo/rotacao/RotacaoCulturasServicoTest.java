package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
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
    private PoliticaDescansoSoloRepositorio repositorio;

    @InjectMocks
    private RotacaoCulturasServico servico;

    // F-08 RN-066 — sem histórico encerrado, cadastro é rejeitado
    @Test
    void deveRejeitarCadastroSemHistoricoEncerrado() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Milho");

        when(repositorio.existeCicloEncerrado(talhaoId, cultura)).thenReturn(false);

        var ex = assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrarIntervalo(talhaoId, cultura, new DiasDescanso(30)));

        assertTrue(ex.getMessage().contains("TALHAO_INVALIDO"));
        verify(repositorio, times(1)).existeCicloEncerrado(talhaoId, cultura);
    }

    // F-08 RN-066 (positivo)
    @Test
    void deveCadastrarIntervaloComHistoricoEncerrado() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");

        when(repositorio.existeCicloEncerrado(talhaoId, cultura)).thenReturn(true);

        assertDoesNotThrow(() -> servico.cadastrarIntervalo(talhaoId, cultura, new DiasDescanso(30)));

        verify(repositorio, times(1)).salvar(any(PoliticaDescansoSolo.class));
    }

    // F-08 RN-068 — dispensa rejeitada quando intervalo já foi cumprido
    @Test
    void deveRejeitarDispensaQuandoIntervaloCumprido() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");
        var politica = new PoliticaDescansoSolo(talhaoId, cultura,
                new DiasDescanso(30), LocalDate.now().minusDays(35));

        when(repositorio.buscarPorTalhaoECultura(talhaoId, cultura)).thenReturn(Optional.of(politica));

        var justificativa = new JustificativaDispensa(
                "Esta eh uma justificativa valida com mais de vinte caracteres");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> servico.concederDispensa(talhaoId, cultura, justificativa, LocalDate.now()));

        assertTrue(ex.getMessage().contains("TALHAO_INVALIDO"));
    }

    // F-08 RN-068 — dispensa rejeitada sem política vigente
    @Test
    void deveRejeitarDispensaSemPoliticaVigente() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Milho");

        when(repositorio.buscarPorTalhaoECultura(talhaoId, cultura)).thenReturn(Optional.empty());

        var justificativa = new JustificativaDispensa(
                "Esta eh uma justificativa valida com mais de vinte caracteres");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> servico.concederDispensa(talhaoId, cultura, justificativa, LocalDate.now()));

        assertTrue(ex.getMessage().contains("TALHAO_INVALIDO"));
    }

    // F-08 RN-067 e RN-068 (positivo) — dispensa concedida dentro do intervalo
    @Test
    void deveConcederDispensaDentroDoIntervalo() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");
        var politica = new PoliticaDescansoSolo(talhaoId, cultura,
                new DiasDescanso(30), LocalDate.now().minusDays(10));

        when(repositorio.buscarPorTalhaoECultura(talhaoId, cultura)).thenReturn(Optional.of(politica));

        var justificativa = new JustificativaDispensa(
                "Justificativa valida com mais de vinte caracteres para dispensa");

        assertDoesNotThrow(() -> servico.concederDispensa(talhaoId, cultura, justificativa, LocalDate.now()));

        verify(repositorio, times(1)).salvar(any(PoliticaDescansoSolo.class));
    }
}

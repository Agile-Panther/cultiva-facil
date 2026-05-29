package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PoliticaDescansoSoloTest {

    @Test
    void deveCriarPoliticaComSucesso() {
        var talhaoId = TalhaoId.novo();
        var cultura = new NomeCultura("Tomate");
        var dias = new DiasDescanso(30);

        var politica = new PoliticaDescansoSolo(talhaoId, cultura, dias, LocalDate.now());

        assertNotNull(politica.getId());
        assertEquals(talhaoId, politica.getTalhaoId());
        assertEquals(cultura, politica.getCultura());
    }

    @Test
    void deveEstarEmDescansoQuandoIntervaloNaoCumprido() {
        var politica = new PoliticaDescansoSolo(
                TalhaoId.novo(), new NomeCultura("Tomate"),
                new DiasDescanso(30), LocalDate.now().minusDays(10));

        assertTrue(politica.estaEmDescanso(LocalDate.now()));
    }

    @Test
    void naoDeveEstarEmDescansoQuandoIntervaloCumprido() {
        var politica = new PoliticaDescansoSolo(
                TalhaoId.novo(), new NomeCultura("Tomate"),
                new DiasDescanso(30), LocalDate.now().minusDays(40));

        assertFalse(politica.estaEmDescanso(LocalDate.now()));
    }

    // F-08 RN-068 — conceder dispensa dentro do intervalo
    @Test
    void deveConcederDispensaDentroDoIntervalo() {
        var politica = new PoliticaDescansoSolo(
                TalhaoId.novo(), new NomeCultura("Tomate"),
                new DiasDescanso(30), LocalDate.now().minusDays(10));

        var justificativa = new JustificativaDispensa(
                "Justificativa valida com mais de vinte caracteres para teste");

        assertDoesNotThrow(() -> politica.concederDispensa(justificativa, LocalDate.now()));
        assertEquals(1, politica.getDispensas().size());
        assertEquals(StatusDispensa.CONCEDIDA, politica.getDispensas().get(0).getStatus());
    }

    // F-08 RN-068 — rejeitar dispensa fora do intervalo
    @Test
    void deveRejeitarDispensaForaDoIntervalo() {
        var politica = new PoliticaDescansoSolo(
                TalhaoId.novo(), new NomeCultura("Tomate"),
                new DiasDescanso(30), LocalDate.now().minusDays(40));

        var justificativa = new JustificativaDispensa(
                "Justificativa valida com mais de vinte caracteres para teste");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> politica.concederDispensa(justificativa, LocalDate.now()));
        assertTrue(ex.getMessage().contains("TALHAO_INVALIDO"));
    }
}

package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoRelatorioTest {

    @Test
    void deveCriarConfiguracaoComDadosValidos() {
        var config = new ConfiguracaoRelatorio("Safra Verao 2026", FiltroPeriodo.SEMESTRE);
        assertEquals("Safra Verao 2026", config.getNome());
        assertEquals(FiltroPeriodo.SEMESTRE, config.getPeriodo());
        assertNotNull(config.getId());
    }

    @Test
    void deveRejeitarNomeComUmCaractere() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new ConfiguracaoRelatorio("A", FiltroPeriodo.SEMESTRE));
        assertEquals("NOME_CONFIG_INVALIDO", ex.getMessage());
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(NullPointerException.class,
                () -> new ConfiguracaoRelatorio(null, FiltroPeriodo.ANO));
    }

    @Test
    void deveRejeitarPeriodoNulo() {
        assertThrows(NullPointerException.class,
                () -> new ConfiguracaoRelatorio("Safra", null));
    }

    @Test
    void deveAceitarTodosOsPeriodos() {
        for (FiltroPeriodo periodo : FiltroPeriodo.values()) {
            var config = new ConfiguracaoRelatorio("Nome " + periodo.name(), periodo);
            assertEquals(periodo, config.getPeriodo());
        }
    }
}

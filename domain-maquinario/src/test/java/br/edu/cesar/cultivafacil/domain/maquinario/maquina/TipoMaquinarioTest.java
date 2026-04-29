package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TipoMaquinarioTest {

    @ParameterizedTest
    @ValueSource(strings = {"Trator", "Colheitadeira", "Pulverizador", "Plantadeira", "ImplementoGeral"})
    void deveAceitarTiposValidos(String tipo) {
        assertDoesNotThrow(() -> TipoMaquinario.de(tipo));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Caminhão", "Carreta", "Drone", "Moto"})
    void deveRejeitarTiposInvalidos(String tipo) {
        assertThrows(IllegalArgumentException.class, () -> TipoMaquinario.de(tipo));
    }

    @Test
    void deveRejeitarValorVazio() {
        assertThrows(IllegalArgumentException.class, () -> TipoMaquinario.de(""));
    }

    @Test
    void deveRejeitarValorNulo() {
        assertThrows(NullPointerException.class, () -> TipoMaquinario.de(null));
    }

    @Test
    void deveMaperarImplementoGeral() {
        assertEquals(TipoMaquinario.IMPLEMENTO_GERAL, TipoMaquinario.de("ImplementoGeral"));
    }
}

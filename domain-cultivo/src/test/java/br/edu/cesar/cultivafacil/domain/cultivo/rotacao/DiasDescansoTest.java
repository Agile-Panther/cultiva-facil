package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiasDescansoTest {

    // F-08 RN-065 — limites válidos (ROTACAO_INVALIDO)
    @Test
    void deveAceitarLimiteInferior() {
        assertDoesNotThrow(() -> new DiasDescanso(30));
    }

    @Test
    void deveAceitarLimiteSuperior() {
        assertDoesNotThrow(() -> new DiasDescanso(3650));
    }

    // F-08 RN-065 — limites inválidos
    @Test
    void deveRejeitarAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(29));
    }

    @Test
    void deveRejeitarAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(3651));
    }

    @Test
    void deveRejeitarNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(-1));
    }
}

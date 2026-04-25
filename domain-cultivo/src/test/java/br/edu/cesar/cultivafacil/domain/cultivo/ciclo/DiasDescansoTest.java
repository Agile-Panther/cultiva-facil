package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiasDescansoTest {

    // F-08 RN-052 — limites validos
    @Test
    void deveAceitarLimiteInferior() {
        assertDoesNotThrow(() -> new DiasDescanso(1));
    }

    @Test
    void deveAceitarLimiteSuperior() {
        assertDoesNotThrow(() -> new DiasDescanso(365));
    }

    // F-08 RN-052 — limites invalidos
    @Test
    void deveRejeitarZeroDias() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(0));
    }

    @Test
    void deveRejeitarAcimaDe365() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(366));
    }
}

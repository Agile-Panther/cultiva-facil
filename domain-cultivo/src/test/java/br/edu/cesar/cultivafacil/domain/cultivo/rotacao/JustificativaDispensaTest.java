package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JustificativaDispensaTest {

    // F-08 RN-067 — limites válidos
    @Test
    void deveAceitarJustificativaComMinimoDeCaracteres() {
        assertDoesNotThrow(() -> new JustificativaDispensa("12345678901234567890"));
    }

    @Test
    void deveAceitarJustificativaComMaximoDeCaracteres() {
        assertDoesNotThrow(() -> new JustificativaDispensa("a".repeat(500)));
    }

    // F-08 RN-067 — limites inválidos
    @Test
    void deveRejeitarJustificativaCurta() {
        assertThrows(IllegalArgumentException.class,
                () -> new JustificativaDispensa("curta"));
    }

    @Test
    void deveRejeitarJustificativaLonga() {
        assertThrows(IllegalArgumentException.class,
                () -> new JustificativaDispensa("a".repeat(501)));
    }

    @Test
    void deveRejeitarJustificativaNula() {
        assertThrows(NullPointerException.class,
                () -> new JustificativaDispensa(null));
    }
}

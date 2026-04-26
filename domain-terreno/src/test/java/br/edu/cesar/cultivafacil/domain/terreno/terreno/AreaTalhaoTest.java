package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class AreaTalhaoTest {

    // F-05 RN-039: area minima de 1 m²
    @Test
    void deveAceitarAreaMinima() {
        assertDoesNotThrow(() -> new AreaTalhao(BigDecimal.ONE));
    }

    @Test
    void deveAceitarAreaValida() {
        var area = new AreaTalhao(new BigDecimal("500.00"));
        assertEquals(0, new BigDecimal("500.00").compareTo(area.getValor()));
    }

    @Test
    void deveRejeitarAreaNula() {
        assertThrows(NullPointerException.class, () -> new AreaTalhao(null));
    }

    // F-05 RN-039: area deve ser no minimo 1 m²
    @Test
    void deveRejeitarAreaZero() {
        assertThrows(IllegalArgumentException.class, () -> new AreaTalhao(BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarAreaNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new AreaTalhao(new BigDecimal("-1")));
    }

    @Test
    void deveRejeitarAreaMenorQueUmMetroQuadrado() {
        assertThrows(IllegalArgumentException.class, () -> new AreaTalhao(new BigDecimal("0.99")));
    }

    @Test
    void deveSerIgualComEscalasDiferentes() {
        assertEquals(new AreaTalhao(new BigDecimal("1.0")), new AreaTalhao(new BigDecimal("1.00")));
    }
}

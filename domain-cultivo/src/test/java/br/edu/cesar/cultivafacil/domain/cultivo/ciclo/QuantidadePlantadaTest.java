package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantidadePlantadaTest {

    @Test
    @DisplayName("deve aceitar quantidade positiva")
    void deveAceitarQuantidadePositiva() {
        assertDoesNotThrow(() -> new QuantidadePlantada(200.00));
    }

    @Test
    @DisplayName("deve rejeitar quantidade zero")
    void deveRejeitarQuantidadeZero() {
        assertThrows(IllegalArgumentException.class, () -> new QuantidadePlantada(0));
    }

    @Test
    @DisplayName("deve rejeitar quantidade negativa")
    void deveRejeitarQuantidadeNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new QuantidadePlantada(-5));
    }

    @Test
    @DisplayName("deve rejeitar mais de tres casas decimais")
    void deveRejeitarMaisDeTresCasasDecimais() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new QuantidadePlantada(10.1234));
        assertTrue(ex.getMessage().contains("QUANTIDADE_INVALIDA"));
    }

    @Test
    @DisplayName("deve aceitar ate tres casas decimais")
    void deveAceitarAteTresCasasDecimais() {
        assertDoesNotThrow(() -> new QuantidadePlantada(10.123));
    }

    @Test
    @DisplayName("deve ser igual quando mesmo valor")
    void deveSerIgualQuandoMesmoValor() {
        QuantidadePlantada qp1 = new QuantidadePlantada(10.50);
        QuantidadePlantada qp2 = new QuantidadePlantada(10.50);
        assertEquals(qp1, qp2);
    }
}

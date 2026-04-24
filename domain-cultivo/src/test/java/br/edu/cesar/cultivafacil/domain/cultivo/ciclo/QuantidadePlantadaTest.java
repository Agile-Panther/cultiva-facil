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
    @DisplayName("deve rejeitar mais de duas casas decimais")
    void deveRejeitarMaisDeDuasCasasDecimais() {
        assertThrows(IllegalArgumentException.class, () -> new QuantidadePlantada(10.123));
    }

    @Test
    @DisplayName("deve ser igual quando mesmo valor")
    void deveSerIgualQuandoMesmoValor() {
        QuantidadePlantada qp1 = new QuantidadePlantada(10.50);
        QuantidadePlantada qp2 = new QuantidadePlantada(10.50);
        assertEquals(qp1, qp2);
    }
}

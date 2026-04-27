package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object IndiceIluminosidade.
 * RN-034: índice entre 2 e 16 horas/dia; campo opcional (null permitido no Terreno).
 */
class IndiceIluminosidadeTest {

    // -------------------------------------------------------------------------
    // Criação válida
    // -------------------------------------------------------------------------

    @Test
    void deveAceitarIndiceNoLimiteMinimo() {
        var indice = new IndiceIluminosidade(new BigDecimal("2"));
        assertEquals(new BigDecimal("2"), indice.getHoras());
    }

    @Test
    void deveAceitarIndiceNoLimiteMaximo() {
        var indice = new IndiceIluminosidade(new BigDecimal("16"));
        assertEquals(new BigDecimal("16"), indice.getHoras());
    }

    @Test
    void deveAceitarIndiceIntermediario() {
        var indice = new IndiceIluminosidade(new BigDecimal("8.5"));
        assertEquals(new BigDecimal("8.5"), indice.getHoras());
    }

    // -------------------------------------------------------------------------
    // RN-034: rejeitar índice fora do intervalo
    // -------------------------------------------------------------------------

    @Test
    void deveRejeitarIndiceAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class,
                () -> new IndiceIluminosidade(new BigDecimal("1.9")));
    }

    @Test
    void deveRejeitarIndiceAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class,
                () -> new IndiceIluminosidade(new BigDecimal("16.1")));
    }

    @Test
    void deveRejeitarIndiceZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new IndiceIluminosidade(BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarIndiceNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new IndiceIluminosidade(new BigDecimal("-5")));
    }

    @Test
    void deveRejeitarIndiceNulo() {
        assertThrows(NullPointerException.class,
                () -> new IndiceIluminosidade(null));
    }

    // -------------------------------------------------------------------------
    // equals e hashCode
    // -------------------------------------------------------------------------

    @Test
    void deveSerIgualAOutroIndiceComMesmoValor() {
        var a = new IndiceIluminosidade(new BigDecimal("8.00"));
        var b = new IndiceIluminosidade(new BigDecimal("8"));
        assertEquals(a, b);
    }

    @Test
    void deveSerDiferenteDeOutroIndiceComValorDistinto() {
        var a = new IndiceIluminosidade(new BigDecimal("6"));
        var b = new IndiceIluminosidade(new BigDecimal("10"));
        assertNotEquals(a, b);
    }

    @Test
    void toString_deveConterHorasPorDia() {
        var indice = new IndiceIluminosidade(new BigDecimal("8"));
        assertTrue(indice.toString().contains("h/day"));
    }
}
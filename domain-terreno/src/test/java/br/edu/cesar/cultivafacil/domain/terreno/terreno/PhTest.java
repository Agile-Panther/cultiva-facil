package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object Ph.
 * RN-033: pH entre 3.0 e 9.0; padrão 6.5 quando omitido.
 */
class PhTest {

    // -------------------------------------------------------------------------
    // Criação válida
    // -------------------------------------------------------------------------

    @Test
    void deveAceitarPhNoLimiteMinimo() {
        var ph = new Ph(new BigDecimal("3.0"));
        assertEquals(new BigDecimal("3.0"), ph.getValor());
    }

    @Test
    void deveAceitarPhNoLimiteMaximo() {
        var ph = new Ph(new BigDecimal("9.0"));
        assertEquals(new BigDecimal("9.0"), ph.getValor());
    }

    @Test
    void deveAceitarPhIntermediario() {
        var ph = new Ph(new BigDecimal("6.5"));
        assertEquals(new BigDecimal("6.5"), ph.getValor());
    }

    // -------------------------------------------------------------------------
    // RN-033: valor padrão 6.5
    // -------------------------------------------------------------------------

    @Test
    void deveCriarPhPadraoComValor6Virgula5() {
        var ph = Ph.padrao();
        assertEquals(new BigDecimal("6.5"), ph.getValor());
    }

    // -------------------------------------------------------------------------
    // RN-033: rejeitar pH fora do intervalo
    // -------------------------------------------------------------------------

    @Test
    void deveRejeitarPhAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ph(new BigDecimal("2.9")));
    }

    @Test
    void deveRejeitarPhAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ph(new BigDecimal("9.1")));
    }

    @Test
    void deveRejeitarPhNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ph(new BigDecimal("-1.0")));
    }

    @Test
    void deveRejeitarPhZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ph(BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarPhNulo() {
        assertThrows(NullPointerException.class,
                () -> new Ph(null));
    }

    // -------------------------------------------------------------------------
    // equals e hashCode
    // -------------------------------------------------------------------------

    @Test
    void deveSerIgualAOutroPhComMesmoValor() {
        var a = new Ph(new BigDecimal("6.5"));
        var b = new Ph(new BigDecimal("6.50"));
        assertEquals(a, b);
    }

    @Test
    void deveSerDiferenteDeOutroPhComValorDistinto() {
        var a = new Ph(new BigDecimal("5.0"));
        var b = new Ph(new BigDecimal("7.0"));
        assertNotEquals(a, b);
    }

    @Test
    void toString_deveRetornarOValorDoPh() {
        var ph = new Ph(new BigDecimal("6.5"));
        assertEquals("6.5", ph.toString());
    }
}
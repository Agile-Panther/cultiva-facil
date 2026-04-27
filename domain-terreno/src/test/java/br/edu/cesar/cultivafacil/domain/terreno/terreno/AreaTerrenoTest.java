package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object AreaTerreno.
 * RN-030: área entre 50 m² e 1.000.000.000 m² (100.000 ha).
 */
class AreaTerrenoTest {

    // -------------------------------------------------------------------------
    // Criação válida
    // -------------------------------------------------------------------------

    @Test
    void deveAceitarAreaNoLimiteMinimo() {
        var area = new AreaTerreno(new BigDecimal("50"));
        assertEquals(new BigDecimal("50"), area.getValorM2());
    }

    @Test
    void deveAceitarAreaNoLimiteMaximo() {
        var area = new AreaTerreno(new BigDecimal("1000000000"));
        assertEquals(new BigDecimal("1000000000"), area.getValorM2());
    }

    @Test
    void deveAceitarAreaIntermediaria() {
        var area = new AreaTerreno(new BigDecimal("5000"));
        assertEquals(new BigDecimal("5000"), area.getValorM2());
    }

    // -------------------------------------------------------------------------
    // RN-030: rejeitar área fora do intervalo
    // -------------------------------------------------------------------------

    @Test
    void deveRejeitarAreaAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class,
                () -> new AreaTerreno(new BigDecimal("49")));
    }

    @Test
    void deveRejeitarAreaZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new AreaTerreno(BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarAreaNegativa() {
        assertThrows(IllegalArgumentException.class,
                () -> new AreaTerreno(new BigDecimal("-1")));
    }

    @Test
    void deveRejeitarAreaAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class,
                () -> new AreaTerreno(new BigDecimal("1000000001")));
    }

    @Test
    void deveRejeitarAreaNula() {
        assertThrows(NullPointerException.class,
                () -> new AreaTerreno(null));
    }

    // -------------------------------------------------------------------------
    // isGreaterThanOrEqual
    // -------------------------------------------------------------------------

    @Test
    void deveRetornarTrueQuandoAreaMaior() {
        var maior = new AreaTerreno(new BigDecimal("200"));
        var menor = new AreaTerreno(new BigDecimal("100"));
        assertTrue(maior.isGreaterThanOrEqual(menor));
    }

    @Test
    void deveRetornarTrueQuandoAreasIguais() {
        var a = new AreaTerreno(new BigDecimal("100"));
        var b = new AreaTerreno(new BigDecimal("100"));
        assertTrue(a.isGreaterThanOrEqual(b));
    }

    @Test
    void deveRetornarFalseQuandoAreaMenor() {
        var menor = new AreaTerreno(new BigDecimal("50"));
        var maior = new AreaTerreno(new BigDecimal("200"));
        assertFalse(menor.isGreaterThanOrEqual(maior));
    }

    @Test
    void deveRejeitarComparaoComAreaNula() {
        var area = new AreaTerreno(new BigDecimal("100"));
        assertThrows(NullPointerException.class,
                () -> area.isGreaterThanOrEqual(null));
    }

    // -------------------------------------------------------------------------
    // equals e hashCode
    // -------------------------------------------------------------------------

    @Test
    void deveSerIgualAOutraAreaComMesmoValor() {
        var a = new AreaTerreno(new BigDecimal("100.00"));
        var b = new AreaTerreno(new BigDecimal("100"));
        assertEquals(a, b);
    }

    @Test
    void deveSerDiferenteDeOutraAreaComValorDistinto() {
        var a = new AreaTerreno(new BigDecimal("100"));
        var b = new AreaTerreno(new BigDecimal("200"));
        assertNotEquals(a, b);
    }

    @Test
    void toString_deveConterUnidadeM2() {
        var area = new AreaTerreno(new BigDecimal("500"));
        assertTrue(area.toString().contains("m²"));
    }
}
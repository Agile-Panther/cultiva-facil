package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValorAreaTest {

    @Test
    void aceitaValorPositivoComDuasCasas() {
        ValorArea area = new ValorArea(new BigDecimal("1.50"));
        assertEquals(0, new BigDecimal("1.50").compareTo(area.getValor()));
    }

    @Test
    void aceitaValorInteiroPositivo() {
        ValorArea area = new ValorArea(new BigDecimal("10"));
        assertEquals(0, new BigDecimal("10").compareTo(area.getValor()));
    }

    @Test
    void aceitaValorComUmaCasaDecimal() {
        ValorArea area = new ValorArea(new BigDecimal("5.5"));
        assertEquals(0, new BigDecimal("5.5").compareTo(area.getValor()));
    }

    @Test
    void rejeitaValorNegativo() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new ValorArea(new BigDecimal("-1.5"))
        );
        assertEquals("VALOR_AREA_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaValorComTresCasasDecimais() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new ValorArea(new BigDecimal("1.567"))
        );
        assertEquals("VALOR_AREA_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaZero() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new ValorArea(BigDecimal.ZERO)
        );
        assertEquals("VALOR_AREA_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaNulo() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new ValorArea(null)
        );
        assertEquals("VALOR_AREA_INVALIDO", ex.getMessage());
    }
}

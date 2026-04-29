package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValorAreaTest {

    @Test
    void aceitaValorInteiroPositivo() {
        ValorArea va = new ValorArea(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), va.getValor());
    }

    @Test
    void aceitaValorPositivoComDuasCasas() {
        ValorArea va = new ValorArea(new BigDecimal("1.25"));
        assertEquals(new BigDecimal("1.25"), va.getValor());
    }

    @Test
    void aceitaValorComUmaCasaDecimal() {
        ValorArea va = new ValorArea(new BigDecimal("5.5"));
        assertEquals(new BigDecimal("5.5"), va.getValor());
    }

    @Test
    void rejeitaNulo() {
        assertThrows(NullPointerException.class, () -> new ValorArea(null));
    }

    @Test
    void rejeitaZero() {
        assertThrows(IllegalArgumentException.class,
            () -> new ValorArea(BigDecimal.ZERO));
    }

    @Test
    void rejeitaValorNegativo() {
        assertThrows(IllegalArgumentException.class,
            () -> new ValorArea(new BigDecimal("-1.0")));
    }

    @Test
    void rejeitaValorComTresCasasDecimais() {
        assertThrows(IllegalArgumentException.class,
            () -> new ValorArea(new BigDecimal("1.234")));
    }
}

package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class HorimetroTest {

    @Test
    void deveAceitarZero() {
        Horimetro h = new Horimetro(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, h.getValor());
    }

    @Test
    void deveAceitarValorPositivo() {
        Horimetro h = new Horimetro(new BigDecimal("100.5"));
        assertEquals(new BigDecimal("100.5"), h.getValor());
    }

    @Test
    void deveRejeitarValorNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horimetro(new BigDecimal("-1")));
    }

    @Test
    void deveRejeitarValorNulo() {
        assertThrows(NullPointerException.class, () -> new Horimetro(null));
    }

    @Test
    void isMaiorQueDeveRetornarTrueQuandoMaior() {
        Horimetro maior = new Horimetro(new BigDecimal("200"));
        Horimetro menor = new Horimetro(new BigDecimal("100"));
        assertTrue(maior.isMaiorQue(menor));
    }

    @Test
    void isMaiorQueDeveRetornarFalseQuandoIgual() {
        Horimetro h1 = new Horimetro(new BigDecimal("100"));
        Horimetro h2 = new Horimetro(new BigDecimal("100"));
        assertFalse(h1.isMaiorQue(h2));
    }

    @Test
    void isMaiorQueDeveRetornarFalseQuandoMenor() {
        Horimetro menor = new Horimetro(new BigDecimal("50"));
        Horimetro maior = new Horimetro(new BigDecimal("100"));
        assertFalse(menor.isMaiorQue(maior));
    }
}

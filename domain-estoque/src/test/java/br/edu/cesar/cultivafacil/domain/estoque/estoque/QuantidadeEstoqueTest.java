package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class QuantidadeEstoqueTest {

    @Test
    void deveAceitarQuantidadePositiva() {
        var quantidade = new QuantidadeEstoque("0.1");

        assertEquals(0, new BigDecimal("0.1").compareTo(quantidade.valor()));
    }

    @Test
    void deveRejeitarQuantidadeNulaZeroOuNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new QuantidadeEstoque((String) null));
        assertThrows(IllegalArgumentException.class, () -> new QuantidadeEstoque(""));
        assertThrows(IllegalArgumentException.class, () -> new QuantidadeEstoque("0"));
        assertThrows(IllegalArgumentException.class, () -> new QuantidadeEstoque("-1"));
    }

    @Test
    void deveSomarESubtrairQuantidade() {
        var quantidade = new QuantidadeEstoque("100");

        assertEquals(0, new BigDecimal("150").compareTo(quantidade.somar(new QuantidadeEstoque("50")).valor()));
        assertEquals(0, new BigDecimal("75").compareTo(quantidade.subtrair(new QuantidadeEstoque("25")).valor()));
    }
}

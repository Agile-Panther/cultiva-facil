package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeMedidaEstoqueTest {

    @Test
    void deveNormalizarUnidade() {
        assertEquals("kg", new UnidadeMedidaEstoque(" kg ").valor());
    }

    @Test
    void deveRejeitarUnidadeVazia() {
        assertThrows(IllegalArgumentException.class, () -> new UnidadeMedidaEstoque(null));
        assertThrows(IllegalArgumentException.class, () -> new UnidadeMedidaEstoque(""));
        assertThrows(IllegalArgumentException.class, () -> new UnidadeMedidaEstoque(" "));
    }
}

package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueTest {

    @Test
    void deveCriarEntidadeEstoqueComIdEPropriedade() {
        PropriedadeId propriedadeId = PropriedadeId.novo();

        Estoque estoque = new Estoque(propriedadeId, LocalDate.of(2026, 4, 28));

        assertNotNull(estoque.id());
        assertEquals(propriedadeId, estoque.propriedadeId());
        assertTrue(estoque.itens().isEmpty());
    }
}

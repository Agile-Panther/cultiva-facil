package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PropriedadeIdTest {

    @Test
    void deveCriarPropriedadeIdValido() {
        UUID valor = UUID.randomUUID();

        PropriedadeId id = new PropriedadeId(valor);

        assertEquals(valor, id.getValor());
        assertEquals(valor.toString(), id.toString());
    }

    @Test
    void deveRejeitarValorNulo() {
        assertThrows(NullPointerException.class, () -> new PropriedadeId(null));
    }

    @Test
    void deveCompararPorValor() {
        UUID valor = UUID.randomUUID();

        assertEquals(new PropriedadeId(valor), new PropriedadeId(valor));
        assertEquals(new PropriedadeId(valor).hashCode(), new PropriedadeId(valor).hashCode());
    }
}

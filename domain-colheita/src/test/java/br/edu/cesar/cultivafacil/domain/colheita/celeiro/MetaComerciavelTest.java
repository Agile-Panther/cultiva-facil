package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetaComerciavelTest {

    @Test
    void deveCriarMetaComValorPositivo() {
        var meta = new MetaComerciavel(150);
        assertEquals(150, meta.getValor(), 0.001);
        assertNotNull(meta.getId());
    }

    @Test
    void deveRejeitarMetaComValorZero() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new MetaComerciavel(0));
        assertEquals("META_COMERCIALIZAVEL_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarMetaComValorNegativo() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new MetaComerciavel(-10));
        assertEquals("META_COMERCIALIZAVEL_INVALIDA", ex.getMessage());
    }

    @Test
    void deveTerIdUnico() {
        var meta1 = new MetaComerciavel(100);
        var meta2 = new MetaComerciavel(100);
        assertNotEquals(meta1.getId(), meta2.getId());
    }
}

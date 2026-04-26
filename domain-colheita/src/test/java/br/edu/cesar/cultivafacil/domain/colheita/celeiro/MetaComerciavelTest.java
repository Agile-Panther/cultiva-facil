package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MetaComerciavelTest {

    @Test
    void deveCriarMetaComValorPositivo() {
        var meta = new MetaComerciavel(BigDecimal.valueOf(150));
        assertEquals(BigDecimal.valueOf(150), meta.getValor());
    }

    @Test
    void deveRejeitarMetaComValorZero() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new MetaComerciavel(BigDecimal.ZERO));
        assertEquals("META_COMERCIALIZAVEL_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarMetaComValorNegativo() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new MetaComerciavel(BigDecimal.valueOf(-10)));
        assertEquals("META_COMERCIALIZAVEL_INVALIDA", ex.getMessage());
    }

    @Test
    void deveTerarIgualdadeEstruturalParaMesmoValor() {
        var meta1 = new MetaComerciavel(BigDecimal.valueOf(100));
        var meta2 = new MetaComerciavel(BigDecimal.valueOf(100));
        assertEquals(meta1, meta2);
    }
}

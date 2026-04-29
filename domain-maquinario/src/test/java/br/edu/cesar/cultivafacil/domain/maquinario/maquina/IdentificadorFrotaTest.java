package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentificadorFrotaTest {

    @Test
    void deveAceitarPlacaValida() {
        IdentificadorFrota id = new IdentificadorFrota("PLC-001");
        assertEquals("PLC-001", id.getValor());
    }

    @Test
    void deveNormalizarParaMaiusculas() {
        IdentificadorFrota id = new IdentificadorFrota("plc-001");
        assertEquals("PLC-001", id.getValor());
    }

    @Test
    void deveRejeitarValorVazio() {
        assertThrows(IllegalArgumentException.class, () -> new IdentificadorFrota(""));
    }

    @Test
    void deveRejeitarValorBranco() {
        assertThrows(IllegalArgumentException.class, () -> new IdentificadorFrota("   "));
    }

    @Test
    void deveRejeitarValorNulo() {
        assertThrows(NullPointerException.class, () -> new IdentificadorFrota(null));
    }

    @Test
    void deveSerIgualComMesmoValor() {
        assertEquals(new IdentificadorFrota("PLC-100"), new IdentificadorFrota("plc-100"));
    }
}

package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeloMaquinaTest {

    @Test
    void deveAceitarMarcaEModeloValidos() {
        ModeloMaquina m = new ModeloMaquina("John Deere", "5075E");
        assertEquals("John Deere", m.getMarca());
        assertEquals("5075E", m.getModelo());
    }

    @Test
    void deveRejeitarMarcaVazia() {
        assertThrows(IllegalArgumentException.class, () -> new ModeloMaquina("", "5075E"));
    }

    @Test
    void deveRejeitarModeloVazio() {
        assertThrows(IllegalArgumentException.class, () -> new ModeloMaquina("John Deere", ""));
    }

    @Test
    void deveRejeitarMarcaNula() {
        assertThrows(NullPointerException.class, () -> new ModeloMaquina(null, "5075E"));
    }

    @Test
    void deveRejeitarModeloNulo() {
        assertThrows(NullPointerException.class, () -> new ModeloMaquina("John Deere", null));
    }

    @Test
    void deveSerIgualComMesmasMarcaEModelo() {
        ModeloMaquina m1 = new ModeloMaquina("John Deere", "5075E");
        ModeloMaquina m2 = new ModeloMaquina("john deere", "5075e");
        assertEquals(m1, m2);
    }
}

package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PropriedadeIdTest {

    @Test
    void deveAceitarUUIDValido() {
        UUID uuid = UUID.randomUUID();
        PropriedadeId id = new PropriedadeId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void deveRejeitarUUIDNulo() {
        assertThrows(NullPointerException.class, () -> new PropriedadeId(null));
    }

    @Test
    void novoDeveGerarUUIDDistinto() {
        PropriedadeId id1 = PropriedadeId.novo();
        PropriedadeId id2 = PropriedadeId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveSerIgualComMesmoUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new PropriedadeId(uuid), new PropriedadeId(uuid));
    }

    @Test
    void deveTerHashCodeConsistente() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new PropriedadeId(uuid).hashCode(), new PropriedadeId(uuid).hashCode());
    }

    @Test
    void toStringDeveRetornarUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new PropriedadeId(uuid).toString());
    }
}

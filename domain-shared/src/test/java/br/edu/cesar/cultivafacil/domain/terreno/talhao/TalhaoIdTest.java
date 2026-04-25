package br.edu.cesar.cultivafacil.domain.terreno.talhao;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TalhaoIdTest {

    @Test
    void deveAceitarUUIDValido() {
        UUID uuid = UUID.randomUUID();
        TalhaoId id = new TalhaoId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void deveRejeitarUUIDNulo() {
        assertThrows(NullPointerException.class, () -> new TalhaoId(null));
    }

    @Test
    void novoDeveGerarUUIDDistinto() {
        TalhaoId id1 = TalhaoId.novo();
        TalhaoId id2 = TalhaoId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveSerIgualComMesmoUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new TalhaoId(uuid), new TalhaoId(uuid));
    }

    @Test
    void deveTerHashCodeConsistente() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new TalhaoId(uuid).hashCode(), new TalhaoId(uuid).hashCode());
    }

    @Test
    void toStringDeveRetornarUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new TalhaoId(uuid).toString());
    }
}

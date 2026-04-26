package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConsorcioCulturaIdTest {

    @Test
    void deveAceitarUUIDValido() {
        UUID uuid = UUID.randomUUID();
        ConsorcioCulturaId id = new ConsorcioCulturaId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void deveRejeitarUUIDNulo() {
        assertThrows(NullPointerException.class, () -> new ConsorcioCulturaId(null));
    }

    @Test
    void novoDeveGerarUUIDDistinto() {
        ConsorcioCulturaId id1 = ConsorcioCulturaId.novo();
        ConsorcioCulturaId id2 = ConsorcioCulturaId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveSerIgualComMesmoUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new ConsorcioCulturaId(uuid), new ConsorcioCulturaId(uuid));
    }

    @Test
    void deveTerHashCodeConsistente() {
        UUID uuid = UUID.randomUUID();
        assertEquals(
            new ConsorcioCulturaId(uuid).hashCode(),
            new ConsorcioCulturaId(uuid).hashCode()
        );
    }

    @Test
    void toStringDeveRetornarUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new ConsorcioCulturaId(uuid).toString());
    }
}

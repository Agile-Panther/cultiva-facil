package br.edu.cesar.cultivafacil.domain.acesso.conta;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContaIdTest {

    @Test
    void deveAceitarUUIDValido() {
        UUID uuid = UUID.randomUUID();
        ContaId id = new ContaId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void deveRejeitarUUIDNulo() {
        assertThrows(NullPointerException.class, () -> new ContaId(null));
    }

    @Test
    void novoDeveGerarUUIDDistinto() {
        ContaId id1 = ContaId.novo();
        ContaId id2 = ContaId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveSerIgualComMesmoUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new ContaId(uuid), new ContaId(uuid));
    }

    @Test
    void deveTerHashCodeConsistente() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new ContaId(uuid).hashCode(), new ContaId(uuid).hashCode());
    }

    @Test
    void toStringDeveRetornarUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new ContaId(uuid).toString());
    }
}

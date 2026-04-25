package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CicloAgricolaIdTest {

    @Test
    void deveAceitarUUIDValido() {
        UUID uuid = UUID.randomUUID();
        CicloAgricolaId id = new CicloAgricolaId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void deveRejeitarUUIDNulo() {
        assertThrows(NullPointerException.class, () -> new CicloAgricolaId(null));
    }

    @Test
    void novoDeveGerarUUIDDistinto() {
        CicloAgricolaId id1 = CicloAgricolaId.novo();
        CicloAgricolaId id2 = CicloAgricolaId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveSerIgualComMesmoUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new CicloAgricolaId(uuid), new CicloAgricolaId(uuid));
    }

    @Test
    void deveTerHashCodeConsistente() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new CicloAgricolaId(uuid).hashCode(), new CicloAgricolaId(uuid).hashCode());
    }

    @Test
    void toStringDeveRetornarUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new CicloAgricolaId(uuid).toString());
    }
}

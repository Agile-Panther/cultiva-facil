package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RelacaoCompatibilidadeIdTest {

    @Test
    void deveAceitarUUIDValido() {
        UUID uuid = UUID.randomUUID();
        RelacaoCompatibilidadeId id = new RelacaoCompatibilidadeId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void deveRejeitarUUIDNulo() {
        assertThrows(NullPointerException.class, () -> new RelacaoCompatibilidadeId(null));
    }

    @Test
    void novoDeveGerarUUIDDistinto() {
        RelacaoCompatibilidadeId id1 = RelacaoCompatibilidadeId.novo();
        RelacaoCompatibilidadeId id2 = RelacaoCompatibilidadeId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveSerIgualComMesmoUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(new RelacaoCompatibilidadeId(uuid), new RelacaoCompatibilidadeId(uuid));
    }

    @Test
    void deveTerHashCodeConsistente() {
        UUID uuid = UUID.randomUUID();
        assertEquals(
            new RelacaoCompatibilidadeId(uuid).hashCode(),
            new RelacaoCompatibilidadeId(uuid).hashCode()
        );
    }

    @Test
    void toStringDeveRetornarUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new RelacaoCompatibilidadeId(uuid).toString());
    }
}

package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object TerrenoId.
 */
class TerrenoIdTest {

    @Test
    void deveCriarTerrenoIdComUuidValido() {
        UUID uuid = UUID.randomUUID();
        var id = new TerrenoId(uuid);
        assertEquals(uuid, id.getValor());
    }

    @Test
    void novo_deveGerarIdUnico() {
        var id1 = TerrenoId.novo();
        var id2 = TerrenoId.novo();
        assertNotEquals(id1, id2);
    }

    @Test
    void deveRejeitarUuidNulo() {
        assertThrows(NullPointerException.class,
                () -> new TerrenoId(null));
    }

    @Test
    void deveSerIgualAOutroIdComMesmoUuid() {
        UUID uuid = UUID.randomUUID();
        var a = new TerrenoId(uuid);
        var b = new TerrenoId(uuid);
        assertEquals(a, b);
    }

    @Test
    void deveSerDiferenteDeOutroIdComUuidDistinto() {
        var a = TerrenoId.novo();
        var b = TerrenoId.novo();
        assertNotEquals(a, b);
    }

    @Test
    void hashCode_deveSerIgualParaIdsComMesmoUuid() {
        UUID uuid = UUID.randomUUID();
        var a = new TerrenoId(uuid);
        var b = new TerrenoId(uuid);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toString_deveRetornarRepresentacaoDoUuid() {
        UUID uuid = UUID.randomUUID();
        var id = new TerrenoId(uuid);
        assertEquals(uuid.toString(), id.toString());
    }
}
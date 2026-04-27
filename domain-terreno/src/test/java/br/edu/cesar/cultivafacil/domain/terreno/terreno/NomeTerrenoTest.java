package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object NomeTerreno.
 * RN-029: nome entre 2 e 100 caracteres, não nulo, não em branco.
 */
class NomeTerrenoTest {

    // -------------------------------------------------------------------------
    // Criação válida
    // -------------------------------------------------------------------------

    @Test
    void deveAceitarNomeValido() {
        var nome = new NomeTerreno("Fazenda Boa Vista");
        assertEquals("Fazenda Boa Vista", nome.getValor());
    }

    @Test
    void deveAceitarNomeComDoisCaracteres() {
        var nome = new NomeTerreno("AB");
        assertEquals("AB", nome.getValor());
    }

    @Test
    void deveAceitarNomeComCemCaracteres() {
        String nomeMax = "A".repeat(100);
        var nome = new NomeTerreno(nomeMax);
        assertEquals(nomeMax, nome.getValor());
    }

    @Test
    void deveFazerTrimDoNome() {
        var nome = new NomeTerreno("  Sítio Esperança  ");
        assertEquals("Sítio Esperança", nome.getValor());
    }

    // -------------------------------------------------------------------------
    // RN-029: rejeitar nome inválido
    // -------------------------------------------------------------------------

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(NullPointerException.class,
                () -> new NomeTerreno(null));
    }

    @Test
    void deveRejeitarNomeEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> new NomeTerreno("   "));
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new NomeTerreno(""));
    }

    @Test
    void deveRejeitarNomeComUmCaractere() {
        assertThrows(IllegalArgumentException.class,
                () -> new NomeTerreno("A"));
    }

    @Test
    void deveRejeitarNomeComCentoEUmCaracteres() {
        String nomeLongo = "A".repeat(101);
        assertThrows(IllegalArgumentException.class,
                () -> new NomeTerreno(nomeLongo));
    }

    // -------------------------------------------------------------------------
    // equals e hashCode
    // -------------------------------------------------------------------------

    @Test
    void deveSerIgualAOutroNomeComMesmoValor() {
        var a = new NomeTerreno("Fazenda");
        var b = new NomeTerreno("Fazenda");
        assertEquals(a, b);
    }

    @Test
    void deveSerDiferenteDeOutroNomeComValorDistinto() {
        var a = new NomeTerreno("Fazenda A");
        var b = new NomeTerreno("Fazenda B");
        assertNotEquals(a, b);
    }

    @Test
    void hashCode_deveSerIgualParaNomesIguais() {
        var a = new NomeTerreno("Sítio");
        var b = new NomeTerreno("Sítio");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toString_deveRetornarOValorDoNome() {
        var nome = new NomeTerreno("Chácara Recanto");
        assertEquals("Chácara Recanto", nome.toString());
    }
}
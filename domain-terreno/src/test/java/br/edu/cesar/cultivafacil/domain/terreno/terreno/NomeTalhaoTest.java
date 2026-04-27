package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NomeTalhaoTest {

    @Test
    void deveAceitarNomeValido() {
        var nome = new NomeTalhao("Canteiro Norte");
        assertEquals("Canteiro Norte", nome.getValor());
    }

    @Test
    void deveAceitarNomeComDoisCaracteres() {
        assertDoesNotThrow(() -> new NomeTalhao("AB"));
    }

    @Test
    void deveAceitarNomeComCemCaracteres() {
        assertDoesNotThrow(() -> new NomeTalhao("A".repeat(100)));
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(NullPointerException.class, () -> new NomeTalhao(null));
    }

    // F-05 RN-040: nome deve ter entre 2 e 100 caracteres
    @Test
    void deveRejeitarNomeComUmCaractere() {
        assertThrows(IllegalArgumentException.class, () -> new NomeTalhao("A"));
    }

    @Test
    void deveRejeitarNomeComCemEUmCaracteres() {
        assertThrows(IllegalArgumentException.class, () -> new NomeTalhao("A".repeat(101)));
    }

    @Test
    void deveRejeitarNomeEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> new NomeTalhao("   "));
    }

    // F-05 RN-040: unicidade e comparacao case-insensitive dentro do Terreno
    @Test
    void deveSerIgualComNomesComDiferencaDeMaiuscula() {
        assertEquals(new NomeTalhao("Canteiro A"), new NomeTalhao("canteiro a"));
    }

    @Test
    void deveTerHashCodeConsistenteComIgualdadeCaseInsensitive() {
        assertEquals(
            new NomeTalhao("Canteiro A").hashCode(),
            new NomeTalhao("CANTEIRO A").hashCode()
        );
    }

    @Test
    void deveArmazenarValorTrimado() {
        assertEquals("AB", new NomeTalhao("  AB  ").getValor());
    }
}

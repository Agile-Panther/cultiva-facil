package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NomeCulturaTest {

    @Test
    @DisplayName("deve criar NomeCultura com valor valido")
    void deveCriarNomeCulturaComValorValido() {
        NomeCultura nome = new NomeCultura("Tomate");
        assertEquals("Tomate", nome.getValor());
    }

    @Test
    @DisplayName("deve fazer trim no valor")
    void deveFazerTrimNoValor() {
        NomeCultura nome = new NomeCultura("  Tomate  ");
        assertEquals("Tomate", nome.getValor());
    }

    @Test
    @DisplayName("deve rejeitar valor nulo")
    void deveRejeitarValorNulo() {
        assertThrows(IllegalArgumentException.class, () -> new NomeCultura(null));
    }

    @Test
    @DisplayName("deve rejeitar valor em branco")
    void deveRejeitarValorEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> new NomeCultura("   "));
    }

    @Test
    @DisplayName("deve ser igual quando mesmo valor")
    void deveSerIgualQuandoMesmoValor() {
        assertEquals(new NomeCultura("Tomate"), new NomeCultura("Tomate"));
    }
}

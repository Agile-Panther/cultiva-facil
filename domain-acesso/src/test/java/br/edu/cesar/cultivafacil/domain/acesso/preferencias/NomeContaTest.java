package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NomeContaTest {

    @Test
    void aceitaNomeValido() {
        NomeConta nome = new NomeConta("Maria da Silva");
        assertEquals("Maria da Silva", nome.getValor());
    }

    @Test
    void aceitaNomeComDoisCaracteres() {
        assertEquals("Jo", new NomeConta("Jo").getValor());
    }

    @Test
    void aceitaNomeComOitentaCaracteres() {
        String oitenta = "A".repeat(80);
        assertEquals(oitenta, new NomeConta(oitenta).getValor());
    }

    @Test
    void trimaNomeComEspacosNasExtremidades() {
        assertEquals("Maria", new NomeConta("  Maria  ").getValor());
    }

    @Test
    void rejeitaNomeComUmCaracter() {
        // RN-023 · F-03
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new NomeConta("M")
        );
        assertEquals("NOME_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaNomeVazio() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new NomeConta("")
        );
        assertEquals("NOME_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaNomeNulo() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new NomeConta(null)
        );
        assertEquals("NOME_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaNomeComOitentaEUmCaracteres() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new NomeConta("A".repeat(81))
        );
        assertEquals("NOME_INVALIDO", ex.getMessage());
    }
}

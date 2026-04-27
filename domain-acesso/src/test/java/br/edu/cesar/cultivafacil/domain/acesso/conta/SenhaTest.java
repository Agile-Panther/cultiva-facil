package br.edu.cesar.cultivafacil.domain.acesso.conta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SenhaTest {

    @Test
    void deveAceitarSenhaValida() {
        Senha senha = new Senha("Senha123");
        assertEquals("Senha123", senha.getValor());
    }

    @Test
    void deveAceitarSenhaComNumeroNoMeio() {
        assertDoesNotThrow(() -> new Senha("abc1defg"));
    }

    @Test
    void deveAceitarSenhaComExatamenteOitoCaracteres() {
        assertDoesNotThrow(() -> new Senha("Senha12!"));
    }

    @Test
    void deveRejeitarSenhaNula() {
        assertThrows(IllegalArgumentException.class, () -> new Senha(null));
    }

    @Test
    void deveRejeitarSenhaComMenosDeOitoCaracteres() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Senha("abc12"));
        assertEquals("SENHA_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarSenhaComSetteCaracteres() {
        assertThrows(IllegalArgumentException.class, () -> new Senha("1234567"));
    }

    @Test
    void deveRejeitarSenhaSemNumero() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Senha("abcdefgh"));
        assertEquals("SENHA_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarSenhaSemNumeroComMaiusculas() {
        assertThrows(IllegalArgumentException.class, () -> new Senha("ABCDEFGH"));
    }
}

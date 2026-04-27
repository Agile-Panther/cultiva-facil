package br.edu.cesar.cultivafacil.domain.acesso.conta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void deveAceitarEmailValido() {
        Email email = new Email("agricultor@fazenda.com");
        assertEquals("agricultor@fazenda.com", email.getValor());
    }

    @Test
    void deveNormalizarEmailParaMinusculo() {
        Email email = new Email("Agricultor@Fazenda.COM");
        assertEquals("agricultor@fazenda.com", email.getValor());
    }

    @Test
    void deveRemoverEspacosDoEmail() {
        Email email = new Email("  joao@email.com  ");
        assertEquals("joao@email.com", email.getValor());
    }

    @Test
    void deveRejeitarEmailNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    void deveRejeitarEmailEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> new Email(""));
    }

    @Test
    void deveRejeitarEmailSemArroba() {
        assertThrows(IllegalArgumentException.class, () -> new Email("semformato.com"));
    }

    @Test
    void deveRejeitarEmailSemDominio() {
        assertThrows(IllegalArgumentException.class, () -> new Email("usuario@"));
    }

    @Test
    void deveRejeitarEmailSemPonto() {
        assertThrows(IllegalArgumentException.class, () -> new Email("usuario@dominio"));
    }

    @Test
    void deveSerIgualComMesmoValor() {
        assertEquals(new Email("a@b.com"), new Email("a@b.com"));
    }

    @Test
    void deveTerHashCodeConsistente() {
        assertEquals(new Email("a@b.com").hashCode(), new Email("a@b.com").hashCode());
    }
}

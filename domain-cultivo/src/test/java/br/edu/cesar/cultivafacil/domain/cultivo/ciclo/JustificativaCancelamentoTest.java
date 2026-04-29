package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JustificativaCancelamentoTest {

    @Test
    void deveCriarComTextoValido() {
        var j = new JustificativaCancelamento("Esta justificativa eh valida pois tem mais de vinte chars");
        assertNotNull(j.getValor());
    }

    @Test
    void deveRejeitarTextoNulo() {
        var ex = assertThrows(NullPointerException.class, () -> new JustificativaCancelamento(null));
        assertTrue(ex.getMessage().contains("JUSTIFICATIVA_INVALIDA"));
    }

    @Test
    void deveRejeitarTextoMenorQue20Caracteres() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new JustificativaCancelamento("curto"));
        assertTrue(ex.getMessage().contains("JUSTIFICATIVA_INVALIDA"));
    }

    @Test
    void deveRejeitarTextoMaiorQue500Caracteres() {
        String longo = "A".repeat(501);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new JustificativaCancelamento(longo));
        assertTrue(ex.getMessage().contains("JUSTIFICATIVA_INVALIDA"));
    }

    @Test
    void deveAceitarExatamente20Caracteres() {
        assertDoesNotThrow(() -> new JustificativaCancelamento("A".repeat(20)));
    }

    @Test
    void deveAceitarExatamente500Caracteres() {
        assertDoesNotThrow(() -> new JustificativaCancelamento("A".repeat(500)));
    }

    @Test
    void deveFazerTrimAntesDaValidacao() {
        // 18 chars + spaces = still only 18 after trim -> should reject
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new JustificativaCancelamento("   curto demais    "));
        assertTrue(ex.getMessage().contains("JUSTIFICATIVA_INVALIDA"));
    }
}

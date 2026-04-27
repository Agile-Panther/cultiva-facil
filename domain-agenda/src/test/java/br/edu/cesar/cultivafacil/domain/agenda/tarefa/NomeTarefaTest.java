package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NomeTarefaTest {

    @Test
    void deveAceitarNomeValido() {
        var nome = new NomeTarefa("Irrigação");
        assertEquals("Irrigação", nome.getValor());
    }

    @Test
    void deveAceitarNomeNoLimiteMinimo() {
        assertDoesNotThrow(() -> new NomeTarefa("AB"));
    }

    @Test
    void deveAceitarNomeNoLimiteMaximo() {
        String nome100 = "A".repeat(100);
        assertDoesNotThrow(() -> new NomeTarefa(nome100));
    }

    // F-09 RN-054 / RN-057
    @Test
    void deveRejeitarNomeNulo() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new NomeTarefa(null));
        assertEquals("NOME_TAREFA_INVALIDO", ex.getMessage());
    }

    // F-09 RN-054 / RN-057
    @Test
    void deveRejeitarNomeBranco() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new NomeTarefa(""));
        assertEquals("NOME_TAREFA_INVALIDO", ex.getMessage());
    }

    // F-09 RN-054 / RN-057
    @Test
    void deveRejeitarNomeComUmCaractere() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new NomeTarefa("A"));
        assertEquals("NOME_TAREFA_INVALIDO", ex.getMessage());
    }

    // F-09 RN-054 / RN-057
    @Test
    void deveRejeitarNomeComCentoeUmCaracteres() {
        String nome101 = "A".repeat(101);
        var ex = assertThrows(IllegalArgumentException.class, () -> new NomeTarefa(nome101));
        assertEquals("NOME_TAREFA_INVALIDO", ex.getMessage());
    }

    @Test
    void deveSerIgualComMesmoValor() {
        assertEquals(new NomeTarefa("Adubação"), new NomeTarefa("Adubação"));
    }

    @Test
    void deveDiferenciarNomesDistintos() {
        assertNotEquals(new NomeTarefa("Irrigação"), new NomeTarefa("Adubação"));
    }
}

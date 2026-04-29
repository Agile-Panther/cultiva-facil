package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoNotificacaoTest {

    @Test
    void aceitaResumoDiario() {
        assertEquals(TipoNotificacao.RESUMO_DIARIO, TipoNotificacao.de("ResumoDiario"));
    }

    @Test
    void aceitaTarefaAtrasada() {
        assertEquals(TipoNotificacao.TAREFA_ATRASADA, TipoNotificacao.de("TarefaAtrasada"));
    }

    @Test
    void aceitaAlertaCritico() {
        assertEquals(TipoNotificacao.ALERTA_CRITICO, TipoNotificacao.de("AlertaCritico"));
    }

    @Test
    void rejeitaNulo() {
        assertThrows(NullPointerException.class, () -> TipoNotificacao.de(null));
    }

    @Test
    void rejeitaStringVazia() {
        assertThrows(IllegalArgumentException.class, () -> TipoNotificacao.de(""));
    }

    @Test
    void rejeitaStringInvalida() {
        assertThrows(IllegalArgumentException.class, () -> TipoNotificacao.de("Promocional"));
    }
}

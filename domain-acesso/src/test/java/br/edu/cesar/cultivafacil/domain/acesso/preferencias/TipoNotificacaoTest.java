package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoNotificacaoTest {

    @Test
    void aceitaResumoDiario() {
        assertEquals(TipoNotificacao.RESUMO_DIARIO, TipoNotificacao.deString("RESUMO_DIARIO"));
    }

    @Test
    void aceitaTarefaAtrasada() {
        assertEquals(TipoNotificacao.TAREFA_ATRASADA, TipoNotificacao.deString("TAREFA_ATRASADA"));
    }

    @Test
    void aceitaAlertaCritico() {
        assertEquals(TipoNotificacao.ALERTA_CRITICO, TipoNotificacao.deString("ALERTA_CRITICO"));
    }

    @Test
    void rejeitaStringInvalida() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> TipoNotificacao.deString("Noticias")
        );
        assertEquals("TIPO_NOTIFICACAO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaStringVazia() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> TipoNotificacao.deString("")
        );
        assertEquals("TIPO_NOTIFICACAO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaNulo() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> TipoNotificacao.deString(null)
        );
        assertEquals("TIPO_NOTIFICACAO_INVALIDO", ex.getMessage());
    }
}

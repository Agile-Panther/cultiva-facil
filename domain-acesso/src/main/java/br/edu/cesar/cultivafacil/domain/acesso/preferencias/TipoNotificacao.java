package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.util.Objects;

public enum TipoNotificacao {
    RESUMO_DIARIO,
    TAREFA_ATRASADA,
    ALERTA_CRITICO;

    public static TipoNotificacao de(String valor) {
        Objects.requireNonNull(valor, "TipoNotificacao nao pode ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("TipoNotificacao nao pode ser vazio");
        }
        switch (valor.trim()) {
            case "ResumoDiario":
            case "RESUMO_DIARIO":
                return RESUMO_DIARIO;
            case "TarefaAtrasada":
            case "TAREFA_ATRASADA":
                return TAREFA_ATRASADA;
            case "AlertaCritico":
            case "ALERTA_CRITICO":
                return ALERTA_CRITICO;
            default:
                throw new IllegalArgumentException("TIPO_NOTIFICACAO_INVALIDO: " + valor);
        }
    }
}

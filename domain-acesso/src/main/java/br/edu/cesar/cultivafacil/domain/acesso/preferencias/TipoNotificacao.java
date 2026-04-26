package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

public enum TipoNotificacao {
    RESUMO_DIARIO, TAREFA_ATRASADA, ALERTA_CRITICO;

    public static TipoNotificacao deString(String valor) {
        if (valor != null) {
            for (TipoNotificacao tipo : values()) {
                if (tipo.name().equals(valor)) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("TIPO_NOTIFICACAO_INVALIDO");
    }
}

package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

public final class LimiteNotificacaoDiaria {

    public static final int VALOR = 5;

    private LimiteNotificacaoDiaria() {
    }

    public static boolean excedido(int notificacoesHoje) {
        return notificacoesHoje >= VALOR;
    }
}

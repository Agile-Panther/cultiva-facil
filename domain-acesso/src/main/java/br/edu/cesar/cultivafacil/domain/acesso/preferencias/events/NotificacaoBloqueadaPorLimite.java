package br.edu.cesar.cultivafacil.domain.acesso.preferencias.events;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.TipoNotificacao;

public class NotificacaoBloqueadaPorLimite {

    private final ContaId contaId;
    private final TipoNotificacao tipo;
    private final int notificacoesHoje;

    public NotificacaoBloqueadaPorLimite(ContaId contaId, TipoNotificacao tipo, int notificacoesHoje) {
        this.contaId = contaId;
        this.tipo = tipo;
        this.notificacoesHoje = notificacoesHoje;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public int getNotificacoesHoje() {
        return notificacoesHoje;
    }
}

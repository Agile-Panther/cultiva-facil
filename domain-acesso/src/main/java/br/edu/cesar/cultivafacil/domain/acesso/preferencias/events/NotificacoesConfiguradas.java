package br.edu.cesar.cultivafacil.domain.acesso.preferencias.events;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.TipoNotificacao;

import java.util.Collections;
import java.util.Set;

public class NotificacoesConfiguradas {

    private final ContaId contaId;
    private final Set<TipoNotificacao> tipos;

    public NotificacoesConfiguradas(ContaId contaId, Set<TipoNotificacao> tipos) {
        this.contaId = contaId;
        this.tipos = Collections.unmodifiableSet(tipos);
    }

    public ContaId getContaId() {
        return contaId;
    }

    public Set<TipoNotificacao> getTipos() {
        return tipos;
    }
}

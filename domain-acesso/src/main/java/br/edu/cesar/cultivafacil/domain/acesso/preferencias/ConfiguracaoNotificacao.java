package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class ConfiguracaoNotificacao {

    private Set<TipoNotificacao> tipos;

    ConfiguracaoNotificacao() {
        this.tipos = new HashSet<>();
    }

    void definir(Set<TipoNotificacao> novosTipos) {
        this.tipos = new HashSet<>(novosTipos);
    }

    Set<TipoNotificacao> getTipos() {
        return Collections.unmodifiableSet(tipos);
    }
}

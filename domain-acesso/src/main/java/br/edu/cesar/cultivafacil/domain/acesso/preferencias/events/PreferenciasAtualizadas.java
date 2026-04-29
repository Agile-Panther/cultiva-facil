package br.edu.cesar.cultivafacil.domain.acesso.preferencias.events;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.PreferenciasId;

public class PreferenciasAtualizadas {

    private final ContaId contaId;
    private final PreferenciasId preferenciasId;

    public PreferenciasAtualizadas(ContaId contaId, PreferenciasId preferenciasId) {
        this.contaId = contaId;
        this.preferenciasId = preferenciasId;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public PreferenciasId getPreferenciasId() {
        return preferenciasId;
    }
}

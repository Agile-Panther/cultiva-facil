package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;

public final class AcessoRevogado {

    private final PropriedadeId propriedadeId;
    private final ContaId contaId;

    public AcessoRevogado(PropriedadeId propriedadeId, ContaId contaId) {
        this.propriedadeId = propriedadeId;
        this.contaId = contaId;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public ContaId getContaId() {
        return contaId;
    }
}

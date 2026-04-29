package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;

public final class MembroAtivado {

    private final PropriedadeId propriedadeId;
    private final ContaId contaId;

    public MembroAtivado(PropriedadeId propriedadeId, ContaId contaId) {
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

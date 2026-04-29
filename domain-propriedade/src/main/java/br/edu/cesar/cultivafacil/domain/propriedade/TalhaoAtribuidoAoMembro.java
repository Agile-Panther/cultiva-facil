package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;
import br.edu.cesar.cultivafacil.shared.TalhaoId;

import java.util.List;

public final class TalhaoAtribuidoAoMembro {

    private final PropriedadeId propriedadeId;
    private final ContaId contaId;
    private final List<TalhaoId> talhoes;

    public TalhaoAtribuidoAoMembro(PropriedadeId propriedadeId, ContaId contaId, List<TalhaoId> talhoes) {
        this.propriedadeId = propriedadeId;
        this.contaId = contaId;
        this.talhoes = talhoes;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public List<TalhaoId> getTalhoes() {
        return talhoes;
    }
}

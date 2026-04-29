package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;

public final class PerfilMembroAlterado {

    private final PropriedadeId propriedadeId;
    private final ContaId contaId;
    private final PerfilAcesso novoPerfil;

    public PerfilMembroAlterado(PropriedadeId propriedadeId, ContaId contaId, PerfilAcesso novoPerfil) {
        this.propriedadeId = propriedadeId;
        this.contaId = contaId;
        this.novoPerfil = novoPerfil;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public PerfilAcesso getNovoPerfil() {
        return novoPerfil;
    }
}

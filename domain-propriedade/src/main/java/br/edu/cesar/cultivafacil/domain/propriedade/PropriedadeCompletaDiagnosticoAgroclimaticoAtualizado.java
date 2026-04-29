package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

public final class PropriedadeCompletaDiagnosticoAgroclimaticoAtualizado {

    private final PropriedadeId propriedadeId;

    public PropriedadeCompletaDiagnosticoAgroclimaticoAtualizado(PropriedadeId propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }
}

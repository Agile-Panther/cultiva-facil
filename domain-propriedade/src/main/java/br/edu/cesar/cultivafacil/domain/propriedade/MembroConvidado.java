package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

public final class MembroConvidado {

    private final PropriedadeId propriedadeId;
    private final String email;
    private final PerfilAcesso perfilAcesso;

    public MembroConvidado(PropriedadeId propriedadeId, String email, PerfilAcesso perfilAcesso) {
        this.propriedadeId = propriedadeId;
        this.email = email;
        this.perfilAcesso = perfilAcesso;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public String getEmail() {
        return email;
    }

    public PerfilAcesso getPerfilAcesso() {
        return perfilAcesso;
    }
}

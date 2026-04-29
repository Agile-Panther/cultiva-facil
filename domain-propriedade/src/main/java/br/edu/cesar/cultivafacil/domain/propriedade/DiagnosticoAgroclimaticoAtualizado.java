package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

public final class DiagnosticoAgroclimaticoAtualizado {

    private final PropriedadeId propriedadeId;
    private final JustificativaDiagnostico justificativa;

    public DiagnosticoAgroclimaticoAtualizado(PropriedadeId propriedadeId, JustificativaDiagnostico justificativa) {
        this.propriedadeId = propriedadeId;
        this.justificativa = justificativa;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public JustificativaDiagnostico getJustificativa() {
        return justificativa;
    }
}

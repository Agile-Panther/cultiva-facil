package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

import java.time.LocalDateTime;

public final class PrazoConvite {

    private final LocalDateTime criadoEm;
    private final LocalDateTime expiraEm;

    public PrazoConvite(LocalDateTime criadoEm, LocalDateTime expiraEm) {
        Validate.notNull(criadoEm, "PROPRIEDADE_INVALIDO");
        Validate.notNull(expiraEm, "PROPRIEDADE_INVALIDO");
        Validate.isTrue(expiraEm.isAfter(criadoEm), "PROPRIEDADE_INVALIDO");
        this.criadoEm = criadoEm;
        this.expiraEm = expiraEm;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }

    public boolean expirado(LocalDateTime momentoAtual) {
        Validate.notNull(momentoAtual, "PROPRIEDADE_INVALIDO");
        return momentoAtual.isAfter(expiraEm);
    }
}

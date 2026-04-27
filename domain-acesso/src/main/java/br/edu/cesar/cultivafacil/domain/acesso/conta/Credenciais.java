package br.edu.cesar.cultivafacil.domain.acesso.conta;

import java.util.Objects;

public final class Credenciais {

    private final Email email;
    private final Senha senha;
    private final boolean consentimento;

    public Credenciais(Email email, Senha senha, boolean consentimento) {
        Objects.requireNonNull(email, "Email é obrigatório");
        Objects.requireNonNull(senha, "Senha é obrigatória");
        this.email = email;
        this.senha = senha;
        this.consentimento = consentimento;
    }

    public Email getEmail() {
        return email;
    }

    public Senha getSenha() {
        return senha;
    }

    public boolean isConsentimento() {
        return consentimento;
    }
}

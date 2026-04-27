package br.edu.cesar.cultivafacil.domain.acesso.conta;

import java.util.Objects;

public class Conta {

    private final ContaId id;
    private final Credenciais credenciais;
    private boolean ativa;

    public Conta(Email email, Senha senha, boolean consentimento) {
        Objects.requireNonNull(email, "Email é obrigatório");
        Objects.requireNonNull(senha, "Senha é obrigatória");

        if (!consentimento) {
            throw new IllegalArgumentException("CONSENTIMENTO_AUSENTE");
        }
        if (senha.getValor().equals(email.getValor())) {
            throw new IllegalArgumentException("SENHA_IGUAL_EMAIL");
        }

        this.id = ContaId.novo();
        this.credenciais = new Credenciais(email, senha, consentimento);
        this.ativa = true;
    }

    public Conta(ContaId id, Credenciais credenciais, boolean ativa) {
        Objects.requireNonNull(id, "ContaId é obrigatório");
        Objects.requireNonNull(credenciais, "Credenciais são obrigatórias");
        this.id = id;
        this.credenciais = credenciais;
        this.ativa = ativa;
    }

    public ContaId getId() {
        return id;
    }

    public Credenciais getCredenciais() {
        return credenciais;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public static class ContaCriada {
        public final ContaId contaId;
        public final Email email;

        public ContaCriada(ContaId contaId, Email email) {
            this.contaId = contaId;
            this.email = email;
        }
    }
}

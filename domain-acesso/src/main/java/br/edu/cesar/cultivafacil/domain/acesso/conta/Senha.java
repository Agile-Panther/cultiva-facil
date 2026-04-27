package br.edu.cesar.cultivafacil.domain.acesso.conta;

import java.util.Objects;

public final class Senha {

    private final String valor;

    public Senha(String valor) {
        if (valor == null || valor.length() < 8) {
            throw new IllegalArgumentException("SENHA_INVALIDA");
        }
        if (!valor.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("SENHA_INVALIDA");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Senha)) return false;
        Senha that = (Senha) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}

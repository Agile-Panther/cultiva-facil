package br.edu.cesar.cultivafacil.domain.acesso.conta;

import java.util.Objects;

public final class Email {

    private final String valor;

    public Email(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        String normalizado = valor.trim().toLowerCase();
        if (!normalizado.matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        this.valor = normalizado;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email that = (Email) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}

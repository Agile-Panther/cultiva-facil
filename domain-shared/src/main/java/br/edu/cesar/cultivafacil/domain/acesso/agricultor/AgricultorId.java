package br.edu.cesar.cultivafacil.domain.acesso.agricultor;

import java.util.Objects;
import java.util.UUID;

public final class AgricultorId {

    private final UUID valor;

    public AgricultorId(UUID valor) {
        Objects.requireNonNull(valor, "AgricultorId não pode ser nulo");
        this.valor = valor;
    }

    public static AgricultorId novo() {
        return new AgricultorId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgricultorId)) return false;
        AgricultorId that = (AgricultorId) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}

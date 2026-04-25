package br.edu.cesar.cultivafacil.domain.acesso.conta;

import java.util.Objects;
import java.util.UUID;

public final class ContaId {

    private final UUID valor;

    public ContaId(UUID valor) {
        Objects.requireNonNull(valor, "ContaId não pode ser nulo");
        this.valor = valor;
    }

    public static ContaId novo() {
        return new ContaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContaId)) return false;
        ContaId that = (ContaId) o;
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

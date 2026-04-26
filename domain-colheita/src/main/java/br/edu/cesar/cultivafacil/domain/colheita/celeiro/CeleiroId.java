package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.util.Objects;
import java.util.UUID;

public final class CeleiroId {

    private final UUID valor;

    public CeleiroId(UUID valor) {
        Objects.requireNonNull(valor, "CeleiroId não pode ser nulo");
        this.valor = valor;
    }

    public static CeleiroId novo() {
        return new CeleiroId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CeleiroId)) return false;
        return valor.equals(((CeleiroId) o).valor);
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

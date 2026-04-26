package br.edu.cesar.cultivafacil.domain.terreno.talhao;

import java.util.Objects;
import java.util.UUID;

public final class TalhaoId {

    private final UUID valor;

    public TalhaoId(UUID valor) {
        Objects.requireNonNull(valor, "TalhaoId não pode ser nulo");
        this.valor = valor;
    }

    public static TalhaoId novo() {
        return new TalhaoId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TalhaoId)) return false;
        TalhaoId that = (TalhaoId) o;
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
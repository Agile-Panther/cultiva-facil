package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import java.util.Objects;
import java.util.UUID;

public final class ColheitaId {
    private final UUID valor;

    public ColheitaId(UUID valor) {
        if (valor == null) throw new IllegalArgumentException("ColheitaId não pode ser nulo");
        this.valor = valor;
    }

    public static ColheitaId novo() {
        return new ColheitaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColheitaId)) return false;
        ColheitaId that = (ColheitaId) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}

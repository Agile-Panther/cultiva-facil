package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.util.Objects;
import java.util.UUID;

public final class TerrenoId {

    private final UUID valor;

    public TerrenoId(UUID valor) {
        Objects.requireNonNull(valor, "TerrenoId cannot be null");
        this.valor = valor;
    }

    public static TerrenoId novo() {
        return new TerrenoId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TerrenoId)) return false;
        return valor.equals(((TerrenoId) o).valor);
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
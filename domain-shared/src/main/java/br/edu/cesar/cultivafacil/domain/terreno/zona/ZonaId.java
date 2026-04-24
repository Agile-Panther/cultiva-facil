package br.edu.cesar.cultivafacil.domain.terreno.zona;

import java.util.Objects;
import java.util.UUID;

public final class ZonaId {

    private final UUID valor;

    public ZonaId(UUID valor) {
        Objects.requireNonNull(valor, "ZonaId não pode ser nulo");
        this.valor = valor;
    }

    public static ZonaId novo() {
        return new ZonaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZonaId)) return false;
        ZonaId that = (ZonaId) o;
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

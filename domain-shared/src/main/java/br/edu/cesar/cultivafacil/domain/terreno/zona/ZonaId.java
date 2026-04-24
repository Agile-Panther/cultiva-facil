package br.edu.cesar.cultivafacil.domain.terreno.zona;

import java.util.Objects;
import java.util.UUID;

public final class ZonaId {

    private final UUID valor;

    public ZonaId(UUID valor) {
        if (valor == null) {
            throw new IllegalArgumentException("ZonaId nao pode ser nulo");
        }
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZonaId that)) {
            return false;
        }
        return Objects.equals(valor, that.valor);
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

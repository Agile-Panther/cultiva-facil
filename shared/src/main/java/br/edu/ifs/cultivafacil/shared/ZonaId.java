package br.edu.ifs.cultivafacil.shared;

import org.apache.commons.lang3.Validate;
import java.util.UUID;

public final class ZonaId {

    private final UUID valor;

    public ZonaId(UUID valor) {
        Validate.notNull(valor, "ZonaId nao pode ser nulo");
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

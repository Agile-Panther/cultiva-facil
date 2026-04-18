package br.com.cultivafacil.domain.manejo.vo;

import java.util.UUID;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public final class ZonaId {

    private final UUID valor;

    public ZonaId(UUID valor) {
        Validate.notNull(valor, "O ID da zona não pode ser nulo.");
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
        if (o == null || getClass() != o.getClass()) return false;
        ZonaId zonaId = (ZonaId) o;
        return Objects.equals(valor, zonaId.valor);
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

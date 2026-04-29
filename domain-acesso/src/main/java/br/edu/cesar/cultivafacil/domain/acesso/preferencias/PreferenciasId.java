package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.util.Objects;
import java.util.UUID;

public final class PreferenciasId {

    private final UUID valor;

    public PreferenciasId(UUID valor) {
        Objects.requireNonNull(valor, "PreferenciasId nao pode ser nulo");
        this.valor = valor;
    }

    public static PreferenciasId novo() {
        return new PreferenciasId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreferenciasId)) return false;
        PreferenciasId that = (PreferenciasId) o;
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

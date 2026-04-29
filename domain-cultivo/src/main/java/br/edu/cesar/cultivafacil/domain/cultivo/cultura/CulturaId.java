package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import java.util.Objects;
import java.util.UUID;

public final class CulturaId {

    private final UUID valor;

    public CulturaId(UUID valor) {
        this.valor = Objects.requireNonNull(valor, "CulturaId nao pode ser nulo");
    }

    public static CulturaId novo() {
        return new CulturaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CulturaId culturaId)) return false;
        return valor.equals(culturaId.valor);
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

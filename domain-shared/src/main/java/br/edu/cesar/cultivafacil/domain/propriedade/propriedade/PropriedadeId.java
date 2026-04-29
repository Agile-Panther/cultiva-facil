package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import java.util.Objects;
import java.util.UUID;

public final class PropriedadeId {

    private final UUID valor;

    public PropriedadeId(UUID valor) {
        this.valor = Objects.requireNonNull(valor, "PropriedadeId nao pode ser nulo");
    }

    public static PropriedadeId novo() {
        return new PropriedadeId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropriedadeId that)) return false;
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

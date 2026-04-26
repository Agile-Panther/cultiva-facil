package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import java.util.Objects;
import java.util.UUID;

public final class ConsorcioCulturaId {

    private final UUID valor;

    public ConsorcioCulturaId(UUID valor) {
        Objects.requireNonNull(valor, "ConsorcioCulturaId nao pode ser nulo");
        this.valor = valor;
    }

    public static ConsorcioCulturaId novo() {
        return new ConsorcioCulturaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsorcioCulturaId that)) return false;
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

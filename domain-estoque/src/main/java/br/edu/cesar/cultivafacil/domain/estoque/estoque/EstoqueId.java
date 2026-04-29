package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.util.Objects;
import java.util.UUID;

public final class EstoqueId {

    private final UUID valor;

    public EstoqueId(UUID valor) {
        Objects.requireNonNull(valor, "EstoqueId nao pode ser nulo");
        this.valor = valor;
    }

    public static EstoqueId novo() {
        return new EstoqueId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstoqueId)) return false;
        EstoqueId estoqueId = (EstoqueId) o;
        return valor.equals(estoqueId.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }
}

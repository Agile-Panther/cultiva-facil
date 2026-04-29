package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.util.Objects;
import java.util.UUID;

public final class ItemEstoqueId {

    private final UUID valor;

    public ItemEstoqueId(UUID valor) {
        Objects.requireNonNull(valor, "ItemEstoqueId nao pode ser nulo");
        this.valor = valor;
    }

    public static ItemEstoqueId novo() {
        return new ItemEstoqueId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEstoqueId)) return false;
        ItemEstoqueId that = (ItemEstoqueId) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }
}

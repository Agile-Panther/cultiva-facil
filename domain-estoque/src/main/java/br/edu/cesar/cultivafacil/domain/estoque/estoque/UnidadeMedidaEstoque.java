package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.util.Objects;

public final class UnidadeMedidaEstoque {

    private final String valor;

    public UnidadeMedidaEstoque(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("UNIDADE_INVALIDA");
        }
        this.valor = valor.trim();
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnidadeMedidaEstoque)) return false;
        UnidadeMedidaEstoque that = (UnidadeMedidaEstoque) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}

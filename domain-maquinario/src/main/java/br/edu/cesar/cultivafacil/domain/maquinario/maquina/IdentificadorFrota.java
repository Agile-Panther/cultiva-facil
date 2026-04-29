package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Objects;

public final class IdentificadorFrota {

    private final String valor;

    public IdentificadorFrota(String valor) {
        Objects.requireNonNull(valor, "IdentificadorFrota nao pode ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("IdentificadorFrota nao pode ser vazio");
        }
        this.valor = valor.trim().toUpperCase();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentificadorFrota)) return false;
        IdentificadorFrota that = (IdentificadorFrota) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public String toString() {
        return valor;
    }
}

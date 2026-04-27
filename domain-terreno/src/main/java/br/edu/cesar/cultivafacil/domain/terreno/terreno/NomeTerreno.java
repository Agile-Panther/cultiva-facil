package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.util.Objects;

public class NomeTerreno {

    private final String valor;

    public NomeTerreno(String valor) {
        Objects.requireNonNull(valor, "Land plot name cannot be null");
        String trimmed = valor.trim();
        if (trimmed.isBlank()) {
            throw new IllegalArgumentException("Land plot name cannot be blank");
        }
        if (trimmed.length() < 2 || trimmed.length() > 100) {
            throw new IllegalArgumentException("Land plot name must be between 2 and 100 characters");
        }
        this.valor = trimmed;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NomeTerreno)) return false;
        return valor.equals(((NomeTerreno) o).valor);
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
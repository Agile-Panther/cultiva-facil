package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import java.util.Objects;

public final class FamiliaBotanica {

    private final String valor;

    public FamiliaBotanica(String valor) {
        this.valor = validar(valor);
    }

    private String validar(String valor) {
        String normalizado = Objects.requireNonNullElse(valor, "").trim();
        if (normalizado.length() < 2 || normalizado.length() > 50) {
            throw new IllegalArgumentException("CULTURA_INVALIDO");
        }
        return normalizado;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FamiliaBotanica that)) return false;
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

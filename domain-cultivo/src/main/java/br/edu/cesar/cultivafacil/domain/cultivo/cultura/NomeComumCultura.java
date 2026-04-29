package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import java.util.Objects;

public final class NomeComumCultura {

    private final String valor;

    public NomeComumCultura(String valor) {
        this.valor = validar(valor);
    }

    private String validar(String valor) {
        String normalizado = Objects.requireNonNullElse(valor, "").trim();
        if (normalizado.length() < 2 || normalizado.length() > 80) {
            throw new IllegalArgumentException("NOME_INVALIDO");
        }
        return normalizado;
    }

    public String getValor() {
        return valor;
    }

    public String chaveNormalizada() {
        return valor.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeComumCultura that)) return false;
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

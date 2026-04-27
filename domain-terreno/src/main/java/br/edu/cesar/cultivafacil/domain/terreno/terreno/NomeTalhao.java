package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.util.Objects;

public class NomeTalhao {

    private final String valor;

    public NomeTalhao(String valor) {
        Objects.requireNonNull(valor, "NomeTalhao nao pode ser nulo");
        String trimado = valor.trim();
        if (trimado.length() < 2 || trimado.length() > 100) {
            throw new IllegalArgumentException("NOME_ZONA_INVALIDO");
        }
        this.valor = trimado;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeTalhao)) return false;
        NomeTalhao that = (NomeTalhao) o;
        return valor.equalsIgnoreCase(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return valor;
    }
}

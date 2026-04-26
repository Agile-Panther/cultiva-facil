package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class NomeConta {

    private final String valor;

    public NomeConta(String valor) {
        String trimmed = valor != null ? valor.trim() : null;
        Validate.isTrue(
            trimmed != null && !trimmed.isEmpty() && trimmed.length() >= 2 && trimmed.length() <= 80,
            "NOME_INVALIDO"
        );
        this.valor = trimmed;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeConta)) return false;
        NomeConta that = (NomeConta) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}

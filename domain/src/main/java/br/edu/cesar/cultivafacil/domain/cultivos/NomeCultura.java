package br.edu.cesar.cultivafacil.domain.cultivos;

import org.apache.commons.lang3.Validate;
import java.util.Objects;

public class NomeCultura {

    private final String valor;

    public NomeCultura(String valor) {
        Validate.notBlank(valor, "NomeCultura nao pode ser vazio");
        Validate.isTrue(valor.trim().length() >= 2 && valor.trim().length() <= 100, "NomeCultura invalido");
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeCultura that)) return false;
        return Objects.equals(valor, that.valor);
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

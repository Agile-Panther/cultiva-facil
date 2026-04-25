package br.edu.cesar.cultivafacil.domain.sanidade.foco;

import br.com.cultivafacil.domain.manejo.exception.DescricaoFocoInvalidaException;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class DescricaoFoco {

    private final String valor;

    public DescricaoFoco(String valor) {
        try {
            Validate.notBlank(valor, "A descrição não pode ser nula ou vazia.");
            Validate.inclusiveBetween(10, 500, valor.length(), "A descrição deve ter entre 10 e 500 caracteres.");
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DescricaoFocoInvalidaException(e.getMessage());
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescricaoFoco that = (DescricaoFoco) o;
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

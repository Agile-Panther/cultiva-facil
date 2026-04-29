package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.math.BigDecimal;
import java.util.Objects;

public final class ValorArea {

    private final BigDecimal valor;

    public ValorArea(BigDecimal valor) {
        Objects.requireNonNull(valor, "ValorArea nao pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("ValorArea deve ser positivo");
        }
        if (valor.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException("ValorArea deve ter no maximo 2 casas decimais");
        }
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValorArea)) return false;
        ValorArea that = (ValorArea) o;
        return valor.compareTo(that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return valor.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}

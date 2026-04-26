package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.Objects;

public final class ValorArea {

    private final BigDecimal valor;

    public ValorArea(BigDecimal valor) {
        Validate.isTrue(valor != null, "VALOR_AREA_INVALIDO");
        Validate.isTrue(valor.compareTo(BigDecimal.ZERO) > 0, "VALOR_AREA_INVALIDO");
        Validate.isTrue(valor.scale() <= 2, "VALOR_AREA_INVALIDO");
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
        return Objects.hash(valor.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return valor.toPlainString();
    }
}

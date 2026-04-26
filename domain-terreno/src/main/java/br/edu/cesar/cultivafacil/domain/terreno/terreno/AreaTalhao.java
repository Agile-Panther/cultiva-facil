package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.math.BigDecimal;
import java.util.Objects;

public class AreaTalhao {

    private final BigDecimal valor;

    public AreaTalhao(BigDecimal valor) {
        Objects.requireNonNull(valor, "AreaTalhao nao pode ser nula");
        if (valor.compareTo(BigDecimal.ONE) < 0) {
            throw new IllegalArgumentException("AREA_ZONA_INVALIDA");
        }
        if (valor.scale() > 2) {
            throw new IllegalArgumentException("AREA_ZONA_INVALIDA");
        }
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AreaTalhao)) return false;
        AreaTalhao that = (AreaTalhao) o;
        return valor.compareTo(that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return valor.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return valor.toPlainString();
    }
}

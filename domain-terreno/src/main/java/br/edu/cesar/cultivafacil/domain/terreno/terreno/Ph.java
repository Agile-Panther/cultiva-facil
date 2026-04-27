package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object representing the soil pH of a land plot.
 * Valid range: 3.0 to 9.0 (RN-033).
 * When not provided at creation, the system defaults to 6.5.
 */
public class Ph {

    private static final BigDecimal MIN_PH = new BigDecimal("3.0");
    private static final BigDecimal MAX_PH = new BigDecimal("9.0");
    public static final BigDecimal DEFAULT_VALUE = new BigDecimal("6.5");

    private final BigDecimal valor;

    public Ph(BigDecimal valor) {
        Objects.requireNonNull(valor, "pH value cannot be null");
        if (valor.compareTo(MIN_PH) < 0 || valor.compareTo(MAX_PH) > 0) {
            throw new IllegalArgumentException("pH must be between 3.0 and 9.0");
        }
        this.valor = valor;
    }

    public static Ph padrao() {
        return new Ph(DEFAULT_VALUE);
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ph)) return false;
        return valor.compareTo(((Ph) o).valor) == 0;
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
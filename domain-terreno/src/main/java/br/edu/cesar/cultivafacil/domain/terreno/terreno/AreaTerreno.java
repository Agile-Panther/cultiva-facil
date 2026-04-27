package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object representing the area of a land plot.
 * Area is stored in square metres (m²).
 * Valid range: 50 m² to 1,000,000,000 m² (= 100,000 ha). RN-030.
 */
public class AreaTerreno {

    private static final BigDecimal MIN_AREA_M2 = new BigDecimal("50");
    private static final BigDecimal MAX_AREA_M2 = new BigDecimal("1000000000");

    private final BigDecimal valorM2;

    public AreaTerreno(BigDecimal valorM2) {
        Objects.requireNonNull(valorM2, "Land plot area cannot be null");
        if (valorM2.compareTo(MIN_AREA_M2) < 0 || valorM2.compareTo(MAX_AREA_M2) > 0) {
            throw new IllegalArgumentException("Land plot area must be between 50 m² and 100,000 ha");
        }
        this.valorM2 = valorM2;
    }

    public BigDecimal getValorM2() {
        return valorM2;
    }

    public boolean isGreaterThanOrEqual(AreaTerreno other) {
        Objects.requireNonNull(other, "Other area cannot be null");
        return this.valorM2.compareTo(other.valorM2) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AreaTerreno)) return false;
        return valorM2.compareTo(((AreaTerreno) o).valorM2) == 0;
    }

    @Override
    public int hashCode() {
        return valorM2.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return valorM2 + " m²";
    }
}
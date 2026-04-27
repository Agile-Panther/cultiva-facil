package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object representing the daily average sunlight index (hours/day) of a land plot.
 * Valid range: 2 to 16 hours (RN-034).
 * This field is optional — null means no light-dependent recommendations are generated.
 */
public class IndiceIluminosidade {

    private static final BigDecimal MIN_HORAS = new BigDecimal("2");
    private static final BigDecimal MAX_HORAS = new BigDecimal("16");

    private final BigDecimal horas;

    public IndiceIluminosidade(BigDecimal horas) {
        Objects.requireNonNull(horas, "Sunlight index cannot be null");
        if (horas.compareTo(MIN_HORAS) < 0 || horas.compareTo(MAX_HORAS) > 0) {
            throw new IllegalArgumentException("Sunlight index must be between 2 and 16 hours per day");
        }
        this.horas = horas;
    }

    public BigDecimal getHoras() {
        return horas;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IndiceIluminosidade)) return false;
        return horas.compareTo(((IndiceIluminosidade) o).horas) == 0;
    }

    @Override
    public int hashCode() {
        return horas.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return horas + " h/day";
    }
}
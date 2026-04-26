package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.math.BigDecimal;
import java.util.Objects;

public final class MetaComerciavel {

    private final BigDecimal valor;

    public MetaComerciavel(BigDecimal valor) {
        Objects.requireNonNull(valor, "Valor não pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("META_COMERCIALIZAVEL_INVALIDA");
        this.valor = valor;
    }

    public boolean projecaoAbaixoDaMeta(BigDecimal projecao) {
        return projecao.compareTo(valor) < 0;
    }

    public BigDecimal getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetaComerciavel)) return false;
        return valor.compareTo(((MetaComerciavel) o).valor) == 0;
    }

    @Override
    public int hashCode() {
        return valor.stripTrailingZeros().hashCode();
    }
}

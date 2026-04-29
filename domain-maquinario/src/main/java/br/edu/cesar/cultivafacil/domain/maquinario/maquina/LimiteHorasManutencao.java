package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.math.BigDecimal;
import java.util.Objects;

public final class LimiteHorasManutencao {

    private final BigDecimal valor;

    public LimiteHorasManutencao(BigDecimal valor) {
        Objects.requireNonNull(valor, "LimiteHorasManutencao nao pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("LIMITE_HORAS_INVALIDO: valor deve ser maior que zero");
        }
        this.valor = valor;
    }

    public static LimiteHorasManutencao de(double valor) {
        return new LimiteHorasManutencao(BigDecimal.valueOf(valor));
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal horasRestantes(Horimetro horimetroAtual) {
        return valor.subtract(horimetroAtual.getValor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LimiteHorasManutencao)) return false;
        LimiteHorasManutencao that = (LimiteHorasManutencao) o;
        return valor.compareTo(that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return valor.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return valor.toPlainString() + "h";
    }
}

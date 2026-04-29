package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.math.BigDecimal;
import java.util.Objects;

public final class Horimetro {

    private final BigDecimal valor;

    public Horimetro(BigDecimal valor) {
        Objects.requireNonNull(valor, "Horimetro nao pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("HORIMETRO_INVALIDO: valor deve ser maior ou igual a zero");
        }
        this.valor = valor;
    }

    public static Horimetro de(double valor) {
        return new Horimetro(BigDecimal.valueOf(valor));
    }

    public boolean isMaiorQue(Horimetro outro) {
        return this.valor.compareTo(outro.valor) > 0;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horimetro)) return false;
        Horimetro that = (Horimetro) o;
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

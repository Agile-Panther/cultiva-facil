package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.math.BigDecimal;
import java.util.Objects;

public final class QuantidadeEstoque {

    public static final QuantidadeEstoque ZERO = new QuantidadeEstoque(BigDecimal.ZERO, true);

    private final BigDecimal valor;

    public QuantidadeEstoque(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA");
        }
        try {
            this.valor = validarPositiva(new BigDecimal(valor.trim()));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA", ex);
        }
    }

    public QuantidadeEstoque(BigDecimal valor) {
        this.valor = validarPositiva(valor);
    }

    private QuantidadeEstoque(BigDecimal valor, boolean permiteZero) {
        Objects.requireNonNull(valor, "Quantidade nao pode ser nula");
        if (!permiteZero && valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA");
        }
        this.valor = valor.stripTrailingZeros();
    }

    static QuantidadeEstoque saldo(BigDecimal valor) {
        return new QuantidadeEstoque(valor, true);
    }

    private static BigDecimal validarPositiva(BigDecimal valor) {
        Objects.requireNonNull(valor, "Quantidade nao pode ser nula");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA");
        }
        return valor.stripTrailingZeros();
    }

    public BigDecimal valor() {
        return valor;
    }

    public QuantidadeEstoque somar(QuantidadeEstoque outra) {
        return saldo(valor.add(outra.valor));
    }

    public QuantidadeEstoque subtrair(QuantidadeEstoque outra) {
        return saldo(valor.subtract(outra.valor));
    }

    public boolean menorQue(QuantidadeEstoque outra) {
        return valor.compareTo(outra.valor) < 0;
    }

    public boolean menorOuIgualA(QuantidadeEstoque outra) {
        return valor.compareTo(outra.valor) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantidadeEstoque)) return false;
        QuantidadeEstoque that = (QuantidadeEstoque) o;
        return valor.compareTo(that.valor) == 0;
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }
}

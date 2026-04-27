package br.edu.cesar.cultivafacil.domain.insumo.plano;

import java.util.Objects;

public class QuantidadeInsumo {

    private final double valor;

    public QuantidadeInsumo(double valor) {
        if (valor <= 0) {
            throw new InsumoDomainException(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA,
                    "Quantidade de insumo deve ser positiva e superior a zero");
        }
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantidadeInsumo)) return false;
        return Double.compare(valor, ((QuantidadeInsumo) o).valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
package br.edu.cesar.cultivafacil.domain.insumo.plano;

import java.math.BigDecimal;
import java.util.Objects;

public class PrecoUnitario {

    private final BigDecimal valor;

    public PrecoUnitario(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsumoDomainException(InsumoErroCodigo.PRECO_UNITARIO_OBRIGATORIO,
                    "Preco unitario deve ser positivo e superior a zero");
        }
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PrecoUnitario)) return false;
        return Objects.equals(valor, ((PrecoUnitario) o).valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

public class QuantidadePlantada {

    private final BigDecimal valor;
    private final UnidadeMedidaCiclo unidade;

    public QuantidadePlantada(BigDecimal valor, UnidadeMedidaCiclo unidade) {
        Validate.notNull(valor, "Quantidade plantada obrigatoria");
        Validate.notNull(unidade, "Unidade de medida obrigatoria");
        Validate.isTrue(valor.compareTo(BigDecimal.ZERO) > 0,
                "Quantidade plantada deve ser superior a zero");
        Validate.isTrue(valor.scale() <= 2,
                "Quantidade plantada deve ter no maximo 2 casas decimais");
        this.valor = valor;
        this.unidade = unidade;
    }

    public BigDecimal getValor() { return valor; }
    public UnidadeMedidaCiclo getUnidade() { return unidade; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantidadePlantada that)) return false;
        return valor.compareTo(that.valor) == 0 && unidade == that.unidade;
    }

    @Override
    public int hashCode() { return valor.hashCode() * 31 + unidade.hashCode(); }
}

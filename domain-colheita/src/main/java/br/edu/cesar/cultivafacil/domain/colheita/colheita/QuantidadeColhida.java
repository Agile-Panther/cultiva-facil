package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import java.math.BigDecimal;

public class QuantidadeColhida {
    private final BigDecimal valor;

    public QuantidadeColhida(BigDecimal valor, BigDecimal maxProjecao) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantidade colhida deve ser positiva");
        if (maxProjecao != null && valor.compareTo(maxProjecao) > 0)
            throw new IllegalArgumentException("Quantidade colhida não pode exceder a projeção do Celeiro");
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }
}

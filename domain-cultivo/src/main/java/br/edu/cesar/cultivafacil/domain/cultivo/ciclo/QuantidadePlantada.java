package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

public class QuantidadePlantada {

    private final double valor;

    public QuantidadePlantada(double valor) {
        Validate.isTrue(valor > 0,
                "QUANTIDADE_INVALIDA: deve ser positivo e superior a zero");
        BigDecimal bd = new BigDecimal(String.valueOf(valor)).stripTrailingZeros();
        Validate.isTrue(bd.scale() <= 3,
                "QUANTIDADE_INVALIDA: deve ter no maximo 3 casas decimais");
        this.valor = valor;
    }

    public double getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantidadePlantada that)) return false;
        return Double.compare(valor, that.valor) == 0;
    }

    @Override
    public int hashCode() { return Double.hashCode(valor); }
}

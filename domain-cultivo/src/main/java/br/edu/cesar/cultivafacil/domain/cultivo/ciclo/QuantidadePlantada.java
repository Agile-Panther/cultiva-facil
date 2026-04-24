package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.apache.commons.lang3.Validate;

public class QuantidadePlantada {

    private final double valor;

    public QuantidadePlantada(double valor) {
        Validate.isTrue(valor > 0,
                "QUANTIDADE_PLANTADA_OBRIGATORIA: deve ser positivo e superior a zero");
        Validate.isTrue(Math.round(valor * 100) == (long) (valor * 100),
                "Quantidade deve ter no maximo 2 casas decimais");
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

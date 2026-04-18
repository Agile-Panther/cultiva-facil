package br.com.cultivafacil.domain.clima.vo;

import br.com.cultivafacil.domain.clima.exception.PrecipitacaoLimiteInvalidaException;
import org.apache.commons.lang3.Validate;

public class PrecipitacaoLimite {

    private final double valor;

    public PrecipitacaoLimite(double valor) {
        try {
            Validate.isTrue(valor >= 0, "A precipitação não pode ser negativa.");
        } catch (IllegalArgumentException e) {
            throw new PrecipitacaoLimiteInvalidaException(e.getMessage());
        }
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}

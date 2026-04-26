package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.exception.PrecipitacaoLimiteInvalidaException;
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

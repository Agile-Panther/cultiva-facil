package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.exception.TemperaturaLimiteInvalidaException;
import org.apache.commons.lang3.Validate;

public class TemperaturaLimite {

    private final double valorMin;
    private final double valorMax;

    public TemperaturaLimite(double valorMin, double valorMax) {
        try {
            Validate.isTrue(valorMin <= valorMax, "A temperatura mínima não pode ser maior que a máxima.");
            Validate.isTrue(valorMin >= -50 && valorMin <= 60, "A temperatura deve estar entre -50 e 60 graus Celsius.");
            Validate.isTrue(valorMax >= -50 && valorMax <= 60, "A temperatura deve estar entre -50 e 60 graus Celsius.");
        } catch (IllegalArgumentException e) {
            throw new TemperaturaLimiteInvalidaException(e.getMessage());
        }
        this.valorMin = valorMin;
        this.valorMax = valorMax;
    }

    public double getValorMin() {
        return valorMin;
    }

    public double getValorMax() {
        return valorMax;
    }
}

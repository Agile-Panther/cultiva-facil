package br.com.cultivafacil.domain.clima.vo;

import br.com.cultivafacil.domain.clima.exception.NecessidadeHidricaInvalidaException;
import org.apache.commons.lang3.Validate;

public class NecessidadeHidrica {

    private final double valor;

    public NecessidadeHidrica(double valor) {
        try {
            Validate.isTrue(valor >= 0, "A necessidade hídrica não pode ser negativa.");
        } catch (IllegalArgumentException e) {
            throw new NecessidadeHidricaInvalidaException(e.getMessage());
        }
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}

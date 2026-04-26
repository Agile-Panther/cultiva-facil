package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.exception.NecessidadeHidricaInvalidaException;
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

package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.exception.JanelaObservacaoInvalidaException;
import org.apache.commons.lang3.Validate;

public class JanelaObservacao {

    private final int dias;

    public JanelaObservacao(int dias) {
        try {
            Validate.isTrue(dias > 0, "A janela de observação deve ser de pelo menos 1 dia.");
        } catch (IllegalArgumentException e) {
            throw new JanelaObservacaoInvalidaException(e.getMessage());
        }
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }
}

package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

public final class JustificativaDiagnostico {

    private final String valor;

    public JustificativaDiagnostico(String valor) {
        Validate.notBlank(valor, "PROPRIEDADE_INVALIDO");
        this.valor = valor.trim();
    }

    public String getValor() {
        return valor;
    }
}

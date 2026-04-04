package br.com.cultivafacil.domain.cultivos.vo;

import br.com.cultivafacil.domain.cultivos.exception.IntervaloInvalidoException;

public class DiasDescanso {

    private final int dias;

    public DiasDescanso(int dias) {
        if (dias < 1 || dias > 365) {
            throw new IntervaloInvalidoException();
        }
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }
}
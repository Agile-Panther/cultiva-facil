package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.apache.commons.lang3.Validate;

public class DiasDescanso {

    private final int valor;

    public DiasDescanso(int valor) {
        Validate.isTrue(valor >= 1 && valor <= 365,
                "INTERVALO_INVALIDO: Intervalo de descanso deve estar entre 1 e 365 dias");
        this.valor = valor;
    }

    public int getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DiasDescanso that)) return false;
        return valor == that.valor;
    }

    @Override
    public int hashCode() { return Integer.hashCode(valor); }
}

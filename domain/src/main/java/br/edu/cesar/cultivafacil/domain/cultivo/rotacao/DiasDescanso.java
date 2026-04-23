package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.apache.commons.lang3.Validate;

public class DiasDescanso {

    private final int dias;

    public DiasDescanso(int dias) {
        Validate.inclusiveBetween(1, 365, dias,
                "Intervalo de descanso deve ser entre 1 e 365 dias");
        this.dias = dias;
    }

    public int getDias() { return dias; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiasDescanso that)) return false;
        return dias == that.dias;
    }

    @Override
    public int hashCode() { return Integer.hashCode(dias); }
}

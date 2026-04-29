package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.time.LocalTime;
import java.util.Objects;

public final class HorarioResumoDiario {

    private static final LocalTime MINIMO = LocalTime.of(5, 0);
    private static final LocalTime MAXIMO = LocalTime.of(10, 0);

    private final LocalTime valor;

    public HorarioResumoDiario(LocalTime valor) {
        Objects.requireNonNull(valor, "HorarioResumoDiario nao pode ser nulo");
        if (valor.isBefore(MINIMO) || valor.isAfter(MAXIMO)) {
            throw new IllegalArgumentException("HORARIO_FORA_DO_INTERVALO");
        }
        this.valor = valor;
    }

    public LocalTime getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorarioResumoDiario)) return false;
        HorarioResumoDiario that = (HorarioResumoDiario) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}

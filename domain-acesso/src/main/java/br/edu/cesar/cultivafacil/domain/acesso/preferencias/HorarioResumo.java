package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.apache.commons.lang3.Validate;

import java.time.LocalTime;
import java.util.Objects;

public final class HorarioResumo {

    // RN-026: entre 05:00 e 10:00 (inclusive nos dois extremos)
    private static final LocalTime MINIMO = LocalTime.of(5, 0);
    private static final LocalTime MAXIMO = LocalTime.of(10, 0);

    private final LocalTime horario;

    public HorarioResumo(LocalTime horario) {
        Validate.isTrue(horario != null, "HORARIO_RESUMO_INVALIDO");
        Validate.isTrue(
            !horario.isBefore(MINIMO) && !horario.isAfter(MAXIMO),
            "HORARIO_RESUMO_INVALIDO"
        );
        this.horario = horario;
    }

    public LocalTime getHorario() {
        return horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorarioResumo)) return false;
        HorarioResumo that = (HorarioResumo) o;
        return horario.equals(that.horario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(horario);
    }

    @Override
    public String toString() {
        return horario.toString();
    }
}

package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioResumoTest {

    // ── Positivos — RN-026: entre 05:00 e 10:00 (inclusive) ───────────────

    @Test
    void aceitaLimiteInferiorInclusive_0500() {
        HorarioResumo h = new HorarioResumo(LocalTime.of(5, 0));
        assertEquals(LocalTime.of(5, 0), h.getHorario());
    }

    @Test
    void aceitaHorarioIntermediario_0700() {
        HorarioResumo h = new HorarioResumo(LocalTime.of(7, 0));
        assertEquals(LocalTime.of(7, 0), h.getHorario());
    }

    @Test
    void aceitaHorarioIntermediario_0930() {
        HorarioResumo h = new HorarioResumo(LocalTime.of(9, 30));
        assertEquals(LocalTime.of(9, 30), h.getHorario());
    }

    @Test
    void aceitaLimiteSuperiorInclusive_1000() {
        HorarioResumo h = new HorarioResumo(LocalTime.of(10, 0));
        assertEquals(LocalTime.of(10, 0), h.getHorario());
    }

    // ── Negativos — RN-026 ────────────────────────────────────────────────

    @Test
    void rejeitaHorarioAbaixoDoMinimo_0400() {
        // RN-026 — cenário principal da US-05
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new HorarioResumo(LocalTime.of(4, 0))
        );
        assertEquals("HORARIO_RESUMO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaHorarioAntesDoMinimo_0459() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new HorarioResumo(LocalTime.of(4, 59))
        );
        assertEquals("HORARIO_RESUMO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaHorarioAposMaximo_1001() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new HorarioResumo(LocalTime.of(10, 1))
        );
        assertEquals("HORARIO_RESUMO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaHorarioMuitoAcimaDoMaximo_1130() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new HorarioResumo(LocalTime.of(11, 30))
        );
        assertEquals("HORARIO_RESUMO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaNulo() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new HorarioResumo(null)
        );
        assertEquals("HORARIO_RESUMO_INVALIDO", ex.getMessage());
    }
}

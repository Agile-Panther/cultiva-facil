package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioResumoTest {

    @Test
    void aceitaLimiteInferiorInclusive_0500() {
        HorarioResumoDiario h = new HorarioResumoDiario(LocalTime.of(5, 0));
        assertEquals(LocalTime.of(5, 0), h.getValor());
    }

    @Test
    void aceitaLimiteSuperiorInclusive_1000() {
        HorarioResumoDiario h = new HorarioResumoDiario(LocalTime.of(10, 0));
        assertEquals(LocalTime.of(10, 0), h.getValor());
    }

    @Test
    void aceitaHorarioIntermediario_0700() {
        HorarioResumoDiario h = new HorarioResumoDiario(LocalTime.of(7, 0));
        assertEquals(LocalTime.of(7, 0), h.getValor());
    }

    @Test
    void aceitaHorarioIntermediario_0930() {
        HorarioResumoDiario h = new HorarioResumoDiario(LocalTime.of(9, 30));
        assertEquals(LocalTime.of(9, 30), h.getValor());
    }

    @Test
    void rejeitaNulo() {
        assertThrows(NullPointerException.class, () -> new HorarioResumoDiario(null));
    }

    @Test
    void rejeitaHorarioAbaixoDoMinimo_0400() {
        assertThrows(IllegalArgumentException.class,
            () -> new HorarioResumoDiario(LocalTime.of(4, 0)));
    }

    @Test
    void rejeitaHorarioAntesDoMinimo_0459() {
        assertThrows(IllegalArgumentException.class,
            () -> new HorarioResumoDiario(LocalTime.of(4, 59)));
    }

    @Test
    void rejeitaHorarioAposMaximo_1001() {
        assertThrows(IllegalArgumentException.class,
            () -> new HorarioResumoDiario(LocalTime.of(10, 1)));
    }

    @Test
    void rejeitaHorarioMuitoAcimaDoMaximo_1130() {
        assertThrows(IllegalArgumentException.class,
            () -> new HorarioResumoDiario(LocalTime.of(11, 30)));
    }
}

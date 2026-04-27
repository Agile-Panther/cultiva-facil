package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DataTarefaTest {

    @Test
    void deveAceitarDataValida() {
        LocalDate hoje = LocalDate.of(2024, 4, 15);
        var data = new DataTarefa(hoje);
        assertEquals(hoje, data.getValor());
    }

    @Test
    void deveRejeitarDataNula() {
        assertThrows(NullPointerException.class, () -> new DataTarefa(null));
    }

    @Test
    void deveSerIgualComMesmaData() {
        LocalDate d = LocalDate.of(2024, 6, 1);
        assertEquals(new DataTarefa(d), new DataTarefa(d));
    }

    @Test
    void deveDiferenciarDatasDistintas() {
        assertNotEquals(
            new DataTarefa(LocalDate.of(2024, 4, 1)),
            new DataTarefa(LocalDate.of(2024, 5, 1))
        );
    }
}

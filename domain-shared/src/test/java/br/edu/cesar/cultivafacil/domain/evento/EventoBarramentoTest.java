package br.edu.cesar.cultivafacil.domain.evento;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventoBarramentoTest {

    static class EventoTeste {
        final String mensagem;
        EventoTeste(String mensagem) { this.mensagem = mensagem; }
    }

    @Test
    void deveEntregarEventoAoObservadorInscrito() {
        EventoBarramento barramento = new EventoBarramento();
        List<EventoTeste> recebidos = new ArrayList<>();

        barramento.inscrever((EventoObservador<EventoTeste>) recebidos::add);
        barramento.publicar(new EventoTeste("plantio iniciado"));

        assertEquals(1, recebidos.size());
        assertEquals("plantio iniciado", recebidos.get(0).mensagem);
    }

    @Test
    void deveEntregarEventoATodosObservadores() {
        EventoBarramento barramento = new EventoBarramento();
        List<String> log = new ArrayList<>();

        barramento.inscrever((EventoObservador<EventoTeste>) e -> log.add("obs1: " + e.mensagem));
        barramento.inscrever((EventoObservador<EventoTeste>) e -> log.add("obs2: " + e.mensagem));
        barramento.publicar(new EventoTeste("colheita registrada"));

        assertEquals(2, log.size());
        assertTrue(log.contains("obs1: colheita registrada"));
        assertTrue(log.contains("obs2: colheita registrada"));
    }

    @Test
    void semObservadoresNaoDeveLancarExcecao() {
        EventoBarramento barramento = new EventoBarramento();
        assertDoesNotThrow(() -> barramento.publicar(new EventoTeste("evento ignorado")));
    }
}

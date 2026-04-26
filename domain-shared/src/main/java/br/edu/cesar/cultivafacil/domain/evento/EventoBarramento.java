package br.edu.cesar.cultivafacil.domain.evento;

import java.util.ArrayList;
import java.util.List;

public class EventoBarramento {

    private final List<EventoObservador<Object>> observadores = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <E> void inscrever(EventoObservador<E> observador) {
        observadores.add((EventoObservador<Object>) observador);
    }

    public void publicar(Object evento) {
        for (EventoObservador<Object> observador : observadores) {
            observador.tratar(evento);
        }
    }
}

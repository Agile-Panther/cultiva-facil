package br.edu.cesar.cultivafacil.domain.evento;

public interface EventoObservador<E> {

    void tratar(E evento);
}

package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import java.util.Objects;
import java.util.UUID;

public final class TarefaId {

    private final UUID valor;

    public TarefaId(UUID valor) {
        Objects.requireNonNull(valor, "TarefaId não pode ser nulo");
        this.valor = valor;
    }

    public static TarefaId novo() {
        return new TarefaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarefaId)) return false;
        TarefaId that = (TarefaId) o;
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

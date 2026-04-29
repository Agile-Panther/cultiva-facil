package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Objects;
import java.util.UUID;

public final class MaquinaId {

    private final UUID valor;

    public MaquinaId(UUID valor) {
        Objects.requireNonNull(valor, "MaquinaId nao pode ser nulo");
        this.valor = valor;
    }

    public static MaquinaId novo() {
        return new MaquinaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaquinaId)) return false;
        MaquinaId that = (MaquinaId) o;
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

package br.edu.cesar.cultivafacil.domain.insumo.plano;

import org.apache.commons.lang3.Validate;
import java.util.Objects;
import java.util.UUID;

public final class PlanoInsumosId {

    private final UUID valor;

    public PlanoInsumosId(UUID valor) {
        Validate.notNull(valor, "PlanoInsumosId nao pode ser nulo");
        this.valor = valor;
    }

    public static PlanoInsumosId novo() {
        return new PlanoInsumosId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlanoInsumosId)) return false;
        return Objects.equals(valor, ((PlanoInsumosId) o).valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
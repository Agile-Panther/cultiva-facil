package br.edu.ifs.cultivafacil.domain.colheita;

import org.apache.commons.lang3.Validate;
import java.util.Objects;
import java.util.UUID;

public class CeleiroId {

    private final UUID valor;

    public CeleiroId(UUID valor) {
        Validate.notNull(valor, "ID do Celeiro não pode ser nulo");
        this.valor = valor;
    }

    public UUID getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CeleiroId that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() { return Objects.hash(valor); }

    @Override
    public String toString() { return "CeleiroId{" + valor + "}"; }
}

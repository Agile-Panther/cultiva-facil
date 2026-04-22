package br.edu.cesar.cultivafacil.domain.cultivos;

import org.apache.commons.lang3.Validate;
import java.util.Objects;
import java.util.UUID;

public final class RelacaoCompatibilidadeId {

    private final UUID valor;

    public RelacaoCompatibilidadeId(UUID valor) {
        Validate.notNull(valor, "RelacaoCompatibilidadeId nao pode ser nulo");
        this.valor = valor;
    }

    public static RelacaoCompatibilidadeId novo() {
        return new RelacaoCompatibilidadeId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelacaoCompatibilidadeId that)) return false;
        return Objects.equals(valor, that.valor);
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

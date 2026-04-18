package br.com.cultivafacil.domain.manejo.vo;

import java.util.UUID;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public final class CicloAgricolaId {

    private final UUID valor;

    public CicloAgricolaId(UUID valor) {
        Validate.notNull(valor, "O ID do ciclo agrícola não pode ser nulo.");
        this.valor = valor;
    }

    public static CicloAgricolaId novo() {
        return new CicloAgricolaId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CicloAgricolaId that = (CicloAgricolaId) o;
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

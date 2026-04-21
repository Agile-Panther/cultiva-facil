package br.edu.ifs.cultivafacil.shared;

import org.apache.commons.lang3.Validate;
import java.util.UUID;

public final class CicloAgricolaId {

    private final UUID valor;

    public CicloAgricolaId(UUID valor) {
        Validate.notNull(valor, "CicloAgricolaId nao pode ser nulo");
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
        if (!(o instanceof CicloAgricolaId)) return false;
        CicloAgricolaId that = (CicloAgricolaId) o;
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

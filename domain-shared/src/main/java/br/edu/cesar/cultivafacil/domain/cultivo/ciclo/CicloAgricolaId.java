package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import java.util.Objects;
import java.util.UUID;

public final class CicloAgricolaId {

    private final UUID valor;

    public CicloAgricolaId(UUID valor) {
        Objects.requireNonNull(valor, "CicloAgricolaId não pode ser nulo");
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
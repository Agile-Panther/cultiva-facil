package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import java.util.Objects;
import java.util.UUID;

public final class CicloAgricolaId {

    private final UUID valor;

    public CicloAgricolaId(UUID valor) {
        if (valor == null) {
            throw new IllegalArgumentException("CicloAgricolaId nao pode ser nulo");
        }
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof CicloAgricolaId that)) {
            return false;
        }
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

package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import java.util.Objects;
import java.util.UUID;

public final class PoliticaDescansoSoloId {

    private final UUID valor;

    public PoliticaDescansoSoloId(UUID valor) {
        Objects.requireNonNull(valor, "PoliticaDescansoSoloId nao pode ser nulo");
        this.valor = valor;
    }

    public static PoliticaDescansoSoloId novo() {
        return new PoliticaDescansoSoloId(UUID.randomUUID());
    }

    public UUID getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoliticaDescansoSoloId that)) return false;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() { return valor.hashCode(); }
}

package br.com.cultivafacil.domain.manejo.vo;

import java.util.UUID;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public final class FocoFitossanitarioId {

    private final UUID valor;

    public FocoFitossanitarioId(UUID valor) {
        Validate.notNull(valor, "O ID não pode ser nulo.");
        this.valor = valor;
    }

    public static FocoFitossanitarioId novo() {
        return new FocoFitossanitarioId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FocoFitossanitarioId that = (FocoFitossanitarioId) o;
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

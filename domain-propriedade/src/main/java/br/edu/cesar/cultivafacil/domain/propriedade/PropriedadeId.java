package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

import java.util.UUID;

public final class PropriedadeId {

    private final UUID valor;

    public PropriedadeId(UUID valor) {
        Validate.notNull(valor, "PROPRIEDADE_INVALIDO");
        this.valor = valor;
    }

    public static PropriedadeId novo() {
        return new PropriedadeId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropriedadeId)) return false;
        PropriedadeId that = (PropriedadeId) o;
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

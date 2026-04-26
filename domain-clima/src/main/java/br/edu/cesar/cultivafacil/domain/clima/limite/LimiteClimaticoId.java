package br.edu.cesar.cultivafacil.domain.clima.limite;

import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class LimiteClimaticoId {

    private final UUID value;

    public LimiteClimaticoId(UUID value) {
        Validate.notNull(value, "O ID do limite climático não pode ser nulo.");
        this.value = value;
    }

    public static LimiteClimaticoId novo() {
        return new LimiteClimaticoId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }
}


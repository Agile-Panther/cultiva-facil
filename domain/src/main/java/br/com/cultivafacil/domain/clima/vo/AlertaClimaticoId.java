package br.com.cultivafacil.domain.clima.vo;

import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class AlertaClimaticoId {

    private final UUID value;

    public AlertaClimaticoId(UUID value) {
        Validate.notNull(value, "O ID do alerta climático não pode ser nulo.");
        this.value = value;
    }

    public static AlertaClimaticoId novo() {
        return new AlertaClimaticoId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }
}


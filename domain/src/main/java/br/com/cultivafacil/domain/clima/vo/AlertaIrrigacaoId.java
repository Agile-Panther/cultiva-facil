package br.com.cultivafacil.domain.clima.vo;

import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class AlertaIrrigacaoId {

    private final UUID value;

    public AlertaIrrigacaoId(UUID value) {
        Validate.notNull(value, "O ID do alerta de irrigação não pode ser nulo.");
        this.value = value;
    }

    public static AlertaIrrigacaoId novo() {
        return new AlertaIrrigacaoId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }
}


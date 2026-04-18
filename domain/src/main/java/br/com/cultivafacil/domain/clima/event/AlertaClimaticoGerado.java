package br.com.cultivafacil.domain.clima.event;

import br.com.cultivafacil.domain.clima.vo.AlertaClimaticoId;

import java.time.Instant;

public class AlertaClimaticoGerado {

    private final AlertaClimaticoId alertaClimaticoId;
    private final Instant occurredOn;

    public AlertaClimaticoGerado(AlertaClimaticoId alertaClimaticoId) {
        this.alertaClimaticoId = alertaClimaticoId;
        this.occurredOn = Instant.now();
    }

    public AlertaClimaticoId getAlertaClimaticoId() {
        return alertaClimaticoId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}


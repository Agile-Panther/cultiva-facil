package br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao;

import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacaoId;

import java.time.Instant;

public class AlertaIrrigacaoGerado {

    private final AlertaIrrigacaoId alertaIrrigacaoId;
    private final Instant occurredOn;

    public AlertaIrrigacaoGerado(AlertaIrrigacaoId alertaIrrigacaoId) {
        this.alertaIrrigacaoId = alertaIrrigacaoId;
        this.occurredOn = Instant.now();
    }

    public AlertaIrrigacaoId getAlertaIrrigacaoId() {
        return alertaIrrigacaoId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}


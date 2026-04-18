package br.com.cultivafacil.domain.clima.event;

import br.com.cultivafacil.domain.clima.vo.LimiteClimaticoId;

import java.time.Instant;

public class LimiteClimaticoDefinido {

    private final LimiteClimaticoId limiteClimaticoId;
    private final Instant occurredOn;

    public LimiteClimaticoDefinido(LimiteClimaticoId limiteClimaticoId) {
        this.limiteClimaticoId = limiteClimaticoId;
        this.occurredOn = Instant.now();
    }

    public LimiteClimaticoId getLimiteClimaticoId() {
        return limiteClimaticoId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}


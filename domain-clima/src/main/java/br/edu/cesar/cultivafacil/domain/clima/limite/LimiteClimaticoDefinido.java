package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimaticoId;

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


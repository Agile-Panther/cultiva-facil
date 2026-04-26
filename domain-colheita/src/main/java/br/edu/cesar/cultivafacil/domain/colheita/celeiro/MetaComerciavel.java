package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.util.UUID;

public class MetaComerciavel {

    private final UUID id;
    private final double valor;

    public MetaComerciavel(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("META_COMERCIALIZAVEL_INVALIDA");
        this.id = UUID.randomUUID();
        this.valor = valor;
    }

    public MetaComerciavel(UUID id, double valor) {
        this.id = id;
        this.valor = valor;
    }

    public UUID getId() { return id; }
    public double getValor() { return valor; }
}

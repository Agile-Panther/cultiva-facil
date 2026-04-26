package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class SaidaCeleiro {

    private final UUID id;
    private final double quantidade;
    private final MotivoSaida motivo;
    private final LocalDateTime registradaEm;

    public SaidaCeleiro(double quantidade, MotivoSaida motivo) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        Objects.requireNonNull(motivo, "Motivo não pode ser nulo");
        this.id = UUID.randomUUID();
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.registradaEm = LocalDateTime.now();
    }

    public SaidaCeleiro(UUID id, double quantidade, MotivoSaida motivo, LocalDateTime registradaEm) {
        this.id = id;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.registradaEm = registradaEm;
    }

    public UUID getId() { return id; }
    public double getQuantidade() { return quantidade; }
    public MotivoSaida getMotivo() { return motivo; }
    public LocalDateTime getRegistradaEm() { return registradaEm; }
}

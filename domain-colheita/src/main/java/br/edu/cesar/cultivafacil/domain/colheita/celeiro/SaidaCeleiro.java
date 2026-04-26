package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class SaidaCeleiro {

    private final UUID id;
    private final BigDecimal quantidade;
    private final MotivoSaida motivo;
    private final LocalDateTime registradaEm;

    public SaidaCeleiro(BigDecimal quantidade, MotivoSaida motivo, LocalDateTime registradaEm) {
        Objects.requireNonNull(quantidade, "Quantidade não pode ser nula");
        Objects.requireNonNull(motivo, "Motivo não pode ser nulo");
        Objects.requireNonNull(registradaEm, "Data de registro não pode ser nula");
        if (quantidade.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        this.id = UUID.randomUUID();
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.registradaEm = registradaEm;
    }

    public SaidaCeleiro(UUID id, BigDecimal quantidade, MotivoSaida motivo, LocalDateTime registradaEm) {
        this.id = id;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.registradaEm = registradaEm;
    }

    public UUID getId() { return id; }
    public BigDecimal getQuantidade() { return quantidade; }
    public MotivoSaida getMotivo() { return motivo; }
    public LocalDateTime getRegistradaEm() { return registradaEm; }
}

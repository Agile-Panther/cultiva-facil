package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ItemCeleiro {

    private final UUID id;
    private final String cultura;
    private final BigDecimal quantidadePlantada;
    private BigDecimal perdasAcumuladas;
    private BigDecimal saldoDisponivel;

    public ItemCeleiro(String cultura, BigDecimal quantidadePlantada) {
        Objects.requireNonNull(cultura, "Cultura não pode ser nula");
        Objects.requireNonNull(quantidadePlantada, "Quantidade plantada não pode ser nula");
        if (cultura.isBlank()) throw new IllegalArgumentException("Cultura não pode ser vazia");
        if (quantidadePlantada.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantidade plantada deve ser positiva");
        this.id = UUID.randomUUID();
        this.cultura = cultura.trim();
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = BigDecimal.ZERO;
        this.saldoDisponivel = quantidadePlantada;
    }

    public ItemCeleiro(UUID id, String cultura, BigDecimal quantidadePlantada,
                       BigDecimal perdasAcumuladas, BigDecimal saldoDisponivel) {
        Objects.requireNonNull(id, "Id não pode ser nulo");
        Objects.requireNonNull(cultura, "Cultura não pode ser nula");
        this.id = id;
        this.cultura = cultura;
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = perdasAcumuladas;
        this.saldoDisponivel = saldoDisponivel;
    }

    void adicionarPerda(BigDecimal quantidade) {
        if (quantidade.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantidade de perda deve ser positiva");
        this.perdasAcumuladas = this.perdasAcumuladas.add(quantidade);
        this.saldoDisponivel = this.saldoDisponivel.subtract(quantidade).max(BigDecimal.ZERO);
    }

    void registrarSaida(BigDecimal quantidade) {
        if (quantidade.compareTo(saldoDisponivel) > 0)
            throw new IllegalArgumentException("SALDO_INSUFICIENTE");
        this.saldoDisponivel = this.saldoDisponivel.subtract(quantidade);
    }

    BigDecimal calcularProjecao() {
        return quantidadePlantada.subtract(perdasAcumuladas);
    }

    public UUID getId() { return id; }
    public String getCultura() { return cultura; }
    public BigDecimal getQuantidadePlantada() { return quantidadePlantada; }
    public BigDecimal getPerdasAcumuladas() { return perdasAcumuladas; }
    public BigDecimal getSaldoDisponivel() { return saldoDisponivel; }
}

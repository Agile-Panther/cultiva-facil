package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.util.Objects;
import java.util.UUID;

public class ItemCeleiro {

    private final UUID id;
    private final String cultura;
    private final double quantidadePlantada;
    private double perdasAcumuladas;
    private double saldoDisponivel;

    public ItemCeleiro(String cultura, double quantidadePlantada) {
        Objects.requireNonNull(cultura, "Cultura não pode ser nula");
        if (cultura.isBlank()) throw new IllegalArgumentException("Cultura não pode ser vazia");
        if (quantidadePlantada <= 0) throw new IllegalArgumentException("Quantidade plantada deve ser positiva");
        this.id = UUID.randomUUID();
        this.cultura = cultura.trim();
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = 0;
        this.saldoDisponivel = quantidadePlantada;
    }

    public ItemCeleiro(UUID id, String cultura, double quantidadePlantada,
                       double perdasAcumuladas, double saldoDisponivel) {
        Objects.requireNonNull(id, "Id não pode ser nulo");
        Objects.requireNonNull(cultura, "Cultura não pode ser nula");
        this.id = id;
        this.cultura = cultura;
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = perdasAcumuladas;
        this.saldoDisponivel = saldoDisponivel;
    }

    void adicionarPerda(double quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade de perda deve ser positiva");
        this.perdasAcumuladas += quantidade;
        this.saldoDisponivel = Math.max(0, this.saldoDisponivel - quantidade);
    }

    void reduzirSaldo(double quantidade) {
        this.saldoDisponivel -= quantidade;
    }

    public UUID getId() { return id; }
    public String getCultura() { return cultura; }
    public double getQuantidadePlantada() { return quantidadePlantada; }
    public double getPerdasAcumuladas() { return perdasAcumuladas; }
    public double getSaldoDisponivel() { return saldoDisponivel; }
}

package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.math.BigDecimal;

public class RelatorioPerda {

    private final BigDecimal quantidadePlantada;
    private final BigDecimal perdasAcumuladas;
    private final BigDecimal projecaoOriginal;
    private final BigDecimal saldoFinal;

    public RelatorioPerda(BigDecimal quantidadePlantada, BigDecimal perdasAcumuladas,
                          BigDecimal projecaoOriginal, BigDecimal saldoFinal) {
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = perdasAcumuladas;
        this.projecaoOriginal = projecaoOriginal;
        this.saldoFinal = saldoFinal;
    }

    public BigDecimal getQuantidadePlantada() { return quantidadePlantada; }
    public BigDecimal getPerdasAcumuladas() { return perdasAcumuladas; }
    public BigDecimal getProjecaoOriginal() { return projecaoOriginal; }
    public BigDecimal getSaldoFinal() { return saldoFinal; }
}

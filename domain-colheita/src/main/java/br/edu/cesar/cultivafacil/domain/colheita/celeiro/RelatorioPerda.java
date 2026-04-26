package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

public class RelatorioPerda {

    private final double quantidadePlantada;
    private final double perdasAcumuladas;
    private final double projecaoOriginal;
    private final double saldoFinal;

    public RelatorioPerda(double quantidadePlantada, double perdasAcumuladas,
                          double projecaoOriginal, double saldoFinal) {
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = perdasAcumuladas;
        this.projecaoOriginal = projecaoOriginal;
        this.saldoFinal = saldoFinal;
    }

    public double getQuantidadePlantada() { return quantidadePlantada; }
    public double getPerdasAcumuladas() { return perdasAcumuladas; }
    public double getProjecaoOriginal() { return projecaoOriginal; }
    public double getSaldoFinal() { return saldoFinal; }
}

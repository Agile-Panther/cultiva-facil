package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.time.LocalDate;
import java.util.Objects;

public final class DataEstimadaManutencao {

    private final LocalDate valor;

    public DataEstimadaManutencao(LocalDate valor) {
        Objects.requireNonNull(valor, "DataEstimadaManutencao nao pode ser nula");
        this.valor = valor;
    }

    public LocalDate getValor() {
        return valor;
    }

    public boolean isNosProximos(int dias, LocalDate hoje) {
        long diasRestantes = hoje.until(valor).toTotalMonths() * 30L;
        diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(hoje, valor);
        return diasRestantes < dias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataEstimadaManutencao)) return false;
        DataEstimadaManutencao that = (DataEstimadaManutencao) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}

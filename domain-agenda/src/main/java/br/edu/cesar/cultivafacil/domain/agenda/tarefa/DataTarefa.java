package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import java.time.LocalDate;
import java.util.Objects;

public class DataTarefa {

    private final LocalDate valor;

    public DataTarefa(LocalDate valor) {
        Objects.requireNonNull(valor, "DataTarefa não pode ser nula");
        this.valor = valor;
    }

    public LocalDate getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataTarefa)) return false;
        DataTarefa that = (DataTarefa) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}

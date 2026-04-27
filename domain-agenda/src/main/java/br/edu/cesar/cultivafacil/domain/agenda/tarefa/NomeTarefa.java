package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import java.util.Objects;

public class NomeTarefa {

    private final String valor;

    public NomeTarefa(String valor) {
        if (valor == null || valor.isBlank() || valor.length() < 2 || valor.length() > 100) {
            throw new IllegalArgumentException("NOME_TAREFA_INVALIDO");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeTarefa)) return false;
        NomeTarefa that = (NomeTarefa) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}

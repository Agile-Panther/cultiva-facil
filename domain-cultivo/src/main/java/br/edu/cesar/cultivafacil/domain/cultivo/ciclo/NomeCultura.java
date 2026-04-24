package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

public class NomeCultura {

    private final String valor;

    public NomeCultura(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Nome da cultura e obrigatorio");
        }
        this.valor = valor.trim();
    }

    public String getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeCultura that)) return false;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() { return valor.hashCode(); }

    @Override
    public String toString() { return valor; }
}

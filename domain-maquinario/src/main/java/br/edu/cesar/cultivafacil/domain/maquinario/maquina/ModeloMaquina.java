package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Objects;

public final class ModeloMaquina {

    private final String marca;
    private final String modelo;

    public ModeloMaquina(String marca, String modelo) {
        Objects.requireNonNull(marca, "Marca nao pode ser nula");
        Objects.requireNonNull(modelo, "Modelo nao pode ser nulo");
        if (marca.isBlank()) throw new IllegalArgumentException("Marca nao pode ser vazia");
        if (modelo.isBlank()) throw new IllegalArgumentException("Modelo nao pode ser vazio");
        this.marca = marca.trim();
        this.modelo = modelo.trim();
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModeloMaquina)) return false;
        ModeloMaquina that = (ModeloMaquina) o;
        return marca.equalsIgnoreCase(that.marca) && modelo.equalsIgnoreCase(that.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca.toLowerCase(), modelo.toLowerCase());
    }

    @Override
    public String toString() {
        return marca + " " + modelo;
    }
}

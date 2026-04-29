package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Objects;
import java.util.UUID;

public class MatrizDesgasteFabricante {

    private final UUID id;
    private final String marca;
    private final String modelo;
    private final LimiteHorasManutencao limiteHoras;

    public MatrizDesgasteFabricante(String marca, String modelo, LimiteHorasManutencao limiteHoras) {
        Objects.requireNonNull(marca, "Marca nao pode ser nula");
        Objects.requireNonNull(modelo, "Modelo nao pode ser nulo");
        Objects.requireNonNull(limiteHoras, "LimiteHorasManutencao nao pode ser nulo");
        if (marca.isBlank()) throw new IllegalArgumentException("Marca nao pode ser vazia");
        if (modelo.isBlank()) throw new IllegalArgumentException("Modelo nao pode ser vazio");
        this.id = UUID.randomUUID();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.limiteHoras = limiteHoras;
    }

    public UUID getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public LimiteHorasManutencao getLimiteHoras() {
        return limiteHoras;
    }
}

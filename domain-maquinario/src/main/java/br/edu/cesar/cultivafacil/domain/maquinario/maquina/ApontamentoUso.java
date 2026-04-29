package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ApontamentoUso {

    private final UUID id;
    private final Horimetro horimetro;
    private final LocalDate data;

    public ApontamentoUso(Horimetro horimetro, LocalDate data) {
        Objects.requireNonNull(horimetro, "Horimetro nao pode ser nulo");
        Objects.requireNonNull(data, "Data nao pode ser nula");
        this.id = UUID.randomUUID();
        this.horimetro = horimetro;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public Horimetro getHorimetro() {
        return horimetro;
    }

    public LocalDate getData() {
        return data;
    }
}

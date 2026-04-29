package br.edu.cesar.cultivafacil.domain.maquinario.maquina.events;

import br.edu.cesar.cultivafacil.domain.maquinario.maquina.Horimetro;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.MaquinaId;

import java.time.LocalDate;

public class UsoMaquinaApontado {

    private final MaquinaId maquinaId;
    private final Horimetro novoHorimetro;
    private final LocalDate data;

    public UsoMaquinaApontado(MaquinaId maquinaId, Horimetro novoHorimetro, LocalDate data) {
        this.maquinaId = maquinaId;
        this.novoHorimetro = novoHorimetro;
        this.data = data;
    }

    public MaquinaId getMaquinaId() {
        return maquinaId;
    }

    public Horimetro getNovoHorimetro() {
        return novoHorimetro;
    }

    public LocalDate getData() {
        return data;
    }
}

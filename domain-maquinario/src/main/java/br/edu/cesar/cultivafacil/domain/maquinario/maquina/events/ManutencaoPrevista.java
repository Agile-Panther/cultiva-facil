package br.edu.cesar.cultivafacil.domain.maquinario.maquina.events;

import br.edu.cesar.cultivafacil.domain.maquinario.maquina.DataEstimadaManutencao;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.MaquinaId;

public class ManutencaoPrevista {

    private final MaquinaId maquinaId;
    private final DataEstimadaManutencao dataEstimada;

    public ManutencaoPrevista(MaquinaId maquinaId, DataEstimadaManutencao dataEstimada) {
        this.maquinaId = maquinaId;
        this.dataEstimada = dataEstimada;
    }

    public MaquinaId getMaquinaId() {
        return maquinaId;
    }

    public DataEstimadaManutencao getDataEstimada() {
        return dataEstimada;
    }
}

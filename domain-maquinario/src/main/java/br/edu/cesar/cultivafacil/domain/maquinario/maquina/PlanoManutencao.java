package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Objects;
import java.util.UUID;

public class PlanoManutencao {

    private final UUID id;
    private DataEstimadaManutencao dataEstimada;
    private boolean alertaEmitido;

    public PlanoManutencao(DataEstimadaManutencao dataEstimada) {
        Objects.requireNonNull(dataEstimada, "DataEstimadaManutencao nao pode ser nula");
        this.id = UUID.randomUUID();
        this.dataEstimada = dataEstimada;
        this.alertaEmitido = false;
    }

    public void atualizarData(DataEstimadaManutencao novaData) {
        Objects.requireNonNull(novaData, "DataEstimadaManutencao nao pode ser nula");
        this.dataEstimada = novaData;
    }

    public void marcarAlertaEmitido() {
        this.alertaEmitido = true;
    }

    public UUID getId() {
        return id;
    }

    public DataEstimadaManutencao getDataEstimada() {
        return dataEstimada;
    }

    public boolean isAlertaEmitido() {
        return alertaEmitido;
    }
}

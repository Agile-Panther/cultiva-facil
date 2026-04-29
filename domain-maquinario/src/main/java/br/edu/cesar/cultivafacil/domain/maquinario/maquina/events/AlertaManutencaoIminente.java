package br.edu.cesar.cultivafacil.domain.maquinario.maquina.events;

import br.edu.cesar.cultivafacil.domain.maquinario.maquina.DataEstimadaManutencao;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.MaquinaId;

import java.math.BigDecimal;

public class AlertaManutencaoIminente {

    private final MaquinaId maquinaId;
    private final BigDecimal horasRestantes;
    private final DataEstimadaManutencao dataEstimada;

    public AlertaManutencaoIminente(MaquinaId maquinaId, BigDecimal horasRestantes,
                                     DataEstimadaManutencao dataEstimada) {
        this.maquinaId = maquinaId;
        this.horasRestantes = horasRestantes;
        this.dataEstimada = dataEstimada;
    }

    public MaquinaId getMaquinaId() {
        return maquinaId;
    }

    public BigDecimal getHorasRestantes() {
        return horasRestantes;
    }

    public DataEstimadaManutencao getDataEstimada() {
        return dataEstimada;
    }
}

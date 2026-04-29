package br.edu.cesar.cultivafacil.domain.cultivo.ciclo.events;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.JustificativaCancelamento;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

public class CicloAgricolaCancelado {

    public final CicloAgricolaId cicloId;
    public final TalhaoId talhaoId;
    public final JustificativaCancelamento justificativa;

    public CicloAgricolaCancelado(CicloAgricolaId cicloId, TalhaoId talhaoId,
                                   JustificativaCancelamento justificativa) {
        this.cicloId = cicloId;
        this.talhaoId = talhaoId;
        this.justificativa = justificativa;
    }
}

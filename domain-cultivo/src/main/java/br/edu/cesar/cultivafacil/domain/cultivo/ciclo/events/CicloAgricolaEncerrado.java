package br.edu.cesar.cultivafacil.domain.cultivo.ciclo.events;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

public class CicloAgricolaEncerrado {

    public final CicloAgricolaId cicloId;
    public final TalhaoId talhaoId;

    public CicloAgricolaEncerrado(CicloAgricolaId cicloId, TalhaoId talhaoId) {
        this.cicloId = cicloId;
        this.talhaoId = talhaoId;
    }
}

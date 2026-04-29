package br.edu.cesar.cultivafacil.domain.cultivo.ciclo.events;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

public class CicloAgricolaAberto {

    public final CicloAgricolaId cicloId;
    public final TalhaoId talhaoId;
    public final NomeCultura cultura;

    public CicloAgricolaAberto(CicloAgricolaId cicloId, TalhaoId talhaoId, NomeCultura cultura) {
        this.cicloId = cicloId;
        this.talhaoId = talhaoId;
        this.cultura = cultura;
    }
}

package br.edu.cesar.cultivafacil.domain.cultivo.rotacao.events;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.DiasDescanso;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.PoliticaDescansoSoloId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

public class IntervaloDescansoDefinido {

    public final PoliticaDescansoSoloId politicaId;
    public final TalhaoId talhaoId;
    public final NomeCultura cultura;
    public final DiasDescanso dias;

    public IntervaloDescansoDefinido(PoliticaDescansoSoloId politicaId, TalhaoId talhaoId,
                                      NomeCultura cultura, DiasDescanso dias) {
        this.politicaId = politicaId;
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.dias = dias;
    }
}

package br.edu.cesar.cultivafacil.domain.cultivo.rotacao.events;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.JustificativaDispensa;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.PoliticaDescansoSoloId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

public class DispensaDescansoConcedida {

    public final PoliticaDescansoSoloId politicaId;
    public final TalhaoId talhaoId;
    public final NomeCultura cultura;
    public final JustificativaDispensa justificativa;

    public DispensaDescansoConcedida(PoliticaDescansoSoloId politicaId, TalhaoId talhaoId,
                                      NomeCultura cultura, JustificativaDispensa justificativa) {
        this.politicaId = politicaId;
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.justificativa = justificativa;
    }
}

package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import java.math.BigDecimal;
import br.edu.cesar.cultivafacil.domain.shared.ZonaId;
import br.edu.cesar.cultivafacil.domain.shared.CicloAgricolaId;

public class ColheitaRegistrada {
    public final ColheitaId colheitaId;
    public final ZonaId zonaId;
    public final CicloAgricolaId cicloAgricolaId;
    public final BigDecimal quantidade;
    public final UnidadeMedida unidadeMedida;
    public final DestinoColheita destino;

    public ColheitaRegistrada(ColheitaId colheitaId, ZonaId zonaId, CicloAgricolaId cicloAgricolaId, BigDecimal quantidade, UnidadeMedida unidadeMedida, DestinoColheita destino) {
        this.colheitaId = colheitaId;
        this.zonaId = zonaId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.destino = destino;
    }
}

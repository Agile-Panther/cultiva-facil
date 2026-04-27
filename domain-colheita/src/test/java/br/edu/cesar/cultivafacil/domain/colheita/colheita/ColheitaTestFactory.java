package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import br.edu.cesar.cultivafacil.domain.shared.ZonaId;
import br.edu.cesar.cultivafacil.domain.shared.CicloAgricolaId;
import java.math.BigDecimal;
import java.util.UUID;

public class ColheitaTestFactory {
    public static Colheita criarColheitaValida() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        return new Colheita(zonaId, cicloId, BigDecimal.valueOf(10), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true);
    }
}

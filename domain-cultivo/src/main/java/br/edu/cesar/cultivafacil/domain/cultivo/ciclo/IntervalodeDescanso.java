package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public class IntervalodeDescanso {

    private final TalhaoId talhaoId;
    private final NomeCultura cultura;
    private final DiasDescanso dias;
    private final LocalDate dataUltimaColheita;

    public IntervalodeDescanso(TalhaoId talhaoId, NomeCultura cultura,
                               DiasDescanso dias, LocalDate dataUltimaColheita) {
        Validate.notNull(talhaoId, "talhaoId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        Validate.notNull(dias, "dias e obrigatorio");
        Validate.notNull(dataUltimaColheita, "dataUltimaColheita e obrigatoria");
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.dias = dias;
        this.dataUltimaColheita = dataUltimaColheita;
    }

    public boolean foiCumprido(LocalDate dataVinculo) {
        return !dataVinculo.isBefore(dataUltimaColheita.plusDays(dias.getValor()));
    }

    public TalhaoId getTalhaoId() { return talhaoId; }
    public NomeCultura getCultura() { return cultura; }
    public DiasDescanso getDias() { return dias; }
    public LocalDate getDataUltimaColheita() { return dataUltimaColheita; }
}

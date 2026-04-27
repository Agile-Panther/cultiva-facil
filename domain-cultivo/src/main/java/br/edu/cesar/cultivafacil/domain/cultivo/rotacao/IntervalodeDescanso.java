package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public class IntervalodeDescanso {

    private final ZonaId zonaId;
    private final NomeCultura cultura;
    private final DiasDescanso dias;
    private final LocalDate dataUltimaColheita;

    public IntervalodeDescanso(ZonaId zonaId, NomeCultura cultura,
                               DiasDescanso dias, LocalDate dataUltimaColheita) {
        Validate.notNull(zonaId, "zonaId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        Validate.notNull(dias, "dias e obrigatorio");
        Validate.notNull(dataUltimaColheita, "dataUltimaColheita e obrigatoria");
        this.zonaId = zonaId;
        this.cultura = cultura;
        this.dias = dias;
        this.dataUltimaColheita = dataUltimaColheita;
    }

    public boolean foiCumprido(LocalDate dataVinculo) {
        return !dataVinculo.isBefore(dataUltimaColheita.plusDays(dias.getValor()));
    }

    public ZonaId getZonaId() { return zonaId; }
    public NomeCultura getCultura() { return cultura; }
    public DiasDescanso getDias() { return dias; }
    public LocalDate getDataUltimaColheita() { return dataUltimaColheita; }
}

package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PoliticaDescansoSolo {

    private final PoliticaDescansoSoloId id;
    private final TalhaoId talhaoId;
    private final NomeCultura cultura;
    private final VersaoIntervaloDescanso versaoAtual;
    private final List<DispensaDescanso> dispensas = new ArrayList<>();

    public PoliticaDescansoSolo(TalhaoId talhaoId, NomeCultura cultura,
                                 DiasDescanso dias, LocalDate dataReferencia) {
        Validate.notNull(talhaoId, "talhaoId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        this.id = PoliticaDescansoSoloId.novo();
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.versaoAtual = new VersaoIntervaloDescanso(dias, dataReferencia);
    }

    public boolean estaEmDescanso(LocalDate data) {
        return !versaoAtual.foiCumprido(data);
    }

    public void concederDispensa(JustificativaDispensa justificativa, LocalDate dataAtual) {
        if (!estaEmDescanso(dataAtual)) {
            throw new IllegalArgumentException(
                    "TALHAO_INVALIDO: o Intervalo de Descanso ja foi cumprido, nao ha restricao a dispensar");
        }
        dispensas.add(new DispensaDescanso(justificativa));
    }

    public PoliticaDescansoSoloId getId() { return id; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public NomeCultura getCultura() { return cultura; }
    public VersaoIntervaloDescanso getVersaoAtual() { return versaoAtual; }
    public List<DispensaDescanso> getDispensas() { return Collections.unmodifiableList(dispensas); }
}

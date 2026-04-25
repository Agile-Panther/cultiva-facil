package br.edu.cesar.cultivafacil.domain.sanidade.foco;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.time.Instant;

public class FocoRegistrado {

    private final FocoFitossanitarioId focoFitossanitarioId;
    private final TalhaoId talhaoId;
    private final CicloAgricolaId cicloAgricolaId;
    private final TipoAgronomicoFoco tipo;
    private final NivelInfestacao nivel;
    private final SeveridadeFoco severidade;
    private final DescricaoFoco descricao;
    private final Instant ocorridoEm;

    public FocoRegistrado(FocoFitossanitarioId focoFitossanitarioId, TalhaoId zonaId, CicloAgricolaId cicloAgricolaId, TipoAgronomicoFoco tipo, NivelInfestacao nivel, SeveridadeFoco severidade, DescricaoFoco descricao) {
        this.focoFitossanitarioId = focoFitossanitarioId;
        this.talhaoId = zonaId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.severidade = severidade;
        this.descricao = descricao;
        this.ocorridoEm = Instant.now();
    }

    public FocoFitossanitarioId getFocoFitossanitarioId() {
        return focoFitossanitarioId;
    }

    public TalhaoId getZonaId() {
        return talhaoId;
    }

    public CicloAgricolaId getCicloAgricolaId() {
        return cicloAgricolaId;
    }

    public TipoAgronomicoFoco getTipo() {
        return tipo;
    }

    public NivelInfestacao getNivel() {
        return nivel;
    }

    public SeveridadeFoco getSeveridade() {
        return severidade;
    }

    public DescricaoFoco getDescricao() {
        return descricao;
    }

    public Instant getOcorridoEm() {
        return ocorridoEm;
    }
}


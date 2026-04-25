package br.edu.cesar.cultivafacil.domain.sanidade.foco;

import br.com.cultivafacil.domain.manejo.vo.*;

import java.time.Instant;

public class FocoRegistrado {

    private final FocoFitossanitarioId focoFitossanitarioId;
    private final ZonaId zonaId;
    private final CicloAgricolaId cicloAgricolaId;
    private final TipoAgronomicoFoco tipo;
    private final NivelInfestacao nivel;
    private final SeveridadeFoco severidade;
    private final DescricaoFoco descricao;
    private final Instant ocorridoEm;

    public FocoRegistrado(FocoFitossanitarioId focoFitossanitarioId, ZonaId zonaId, CicloAgricolaId cicloAgricolaId, TipoAgronomicoFoco tipo, NivelInfestacao nivel, SeveridadeFoco severidade, DescricaoFoco descricao) {
        this.focoFitossanitarioId = focoFitossanitarioId;
        this.zonaId = zonaId;
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

    public ZonaId getZonaId() {
        return zonaId;
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


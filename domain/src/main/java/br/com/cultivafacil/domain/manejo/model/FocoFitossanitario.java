package br.com.cultivafacil.domain.manejo.model;

import br.com.cultivafacil.domain.manejo.event.FocoRegistrado;
import br.com.cultivafacil.domain.manejo.vo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FocoFitossanitario {

    private FocoFitossanitarioId id;
    private ZonaId zonaId;
    private CicloAgricolaId cicloAgricolaId;
    private TipoAgronomicoFoco tipo;
    private NivelInfestacao nivel;
    private SeveridadeFoco severidade;
    private DescricaoFoco descricao;

    private final transient List<Object> domainEvents = new ArrayList<>();

    // Construtor para criação
    public FocoFitossanitario(ZonaId zonaId, CicloAgricolaId cicloAgricolaId, TipoAgronomicoFoco tipo, NivelInfestacao nivel, SeveridadeFoco severidade, DescricaoFoco descricao) {
        Objects.requireNonNull(zonaId, "O ID da zona não pode ser nulo.");
        Objects.requireNonNull(cicloAgricolaId, "O ID do ciclo agrícola não pode ser nulo.");
        Objects.requireNonNull(tipo, "O tipo não pode ser nulo.");
        Objects.requireNonNull(nivel, "O nível não pode ser nulo.");
        Objects.requireNonNull(severidade, "A severidade não pode ser nula.");
        Objects.requireNonNull(descricao, "A descrição não pode ser nula.");

        this.id = FocoFitossanitarioId.novo();
        this.zonaId = zonaId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.severidade = severidade;
        this.descricao = descricao;

        this.domainEvents.add(new FocoRegistrado(this.id, this.zonaId, this.cicloAgricolaId, this.tipo, this.nivel, this.severidade, this.descricao));
    }

    // Construtor para reconstituição
    public FocoFitossanitario(FocoFitossanitarioId id, ZonaId zonaId, CicloAgricolaId cicloAgricolaId, TipoAgronomicoFoco tipo, NivelInfestacao nivel, SeveridadeFoco severidade, DescricaoFoco descricao) {
        this.id = id;
        this.zonaId = zonaId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.severidade = severidade;
        this.descricao = descricao;
    }

    public FocoFitossanitarioId getId() {
        return id;
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

    public List<Object> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}


package br.edu.cesar.cultivafacil.domain.sanidade.foco;

import br.edu.cesar.cultivafacil.domain.sanidade.foco.FocoRegistrado;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FocoFitossanitario {

    private FocoFitossanitarioId id;
    private TalhaoId talhaoId;
    private CicloAgricolaId cicloAgricolaId;
    private TipoAgronomicoFoco tipo;
    private NivelInfestacao nivel;
    private SeveridadeFoco severidade;
    private DescricaoFoco descricao;

    private final transient List<Object> domainEvents = new ArrayList<>();

    // Construtor para criação
    public FocoFitossanitario(TalhaoId talhaoId, CicloAgricolaId cicloAgricolaId, TipoAgronomicoFoco tipo, NivelInfestacao nivel, SeveridadeFoco severidade, DescricaoFoco descricao) {
        Objects.requireNonNull(talhaoId, "O ID da zona não pode ser nulo.");
        Objects.requireNonNull(cicloAgricolaId, "O ID do ciclo agrícola não pode ser nulo.");
        Objects.requireNonNull(tipo, "O tipo não pode ser nulo.");
        Objects.requireNonNull(nivel, "O nível não pode ser nulo.");
        Objects.requireNonNull(severidade, "A severidade não pode ser nula.");
        Objects.requireNonNull(descricao, "A descrição não pode ser nula.");

        this.id = FocoFitossanitarioId.novo();
        this.talhaoId = talhaoId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.severidade = severidade;
        this.descricao = descricao;

        this.domainEvents.add(new FocoRegistrado(this.id, this.talhaoId, this.cicloAgricolaId, this.tipo, this.nivel, this.severidade, this.descricao));
    }

    // Construtor para reconstituição
    public FocoFitossanitario(FocoFitossanitarioId id, TalhaoId talhaoId, CicloAgricolaId cicloAgricolaId, TipoAgronomicoFoco tipo, NivelInfestacao nivel, SeveridadeFoco severidade, DescricaoFoco descricao) {
        this.id = id;
        this.talhaoId = talhaoId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.severidade = severidade;
        this.descricao = descricao;
    }

    public FocoFitossanitarioId getId() {
        return id;
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

    public List<Object> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}


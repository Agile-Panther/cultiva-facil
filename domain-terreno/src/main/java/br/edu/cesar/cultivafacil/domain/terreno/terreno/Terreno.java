package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate Root for a land plot (Terreno) in the terrain subdomain.
 * <p>
 * Owns: NomeTerreno, AreaTerreno, TipoSoloTerreno, ClimaRegiaoTerreno, pH, IndiceIluminosidade.
 * Publishes domain events: TerrenoCriado (reserved for future use — Entrega 2 integration).
 * </p>
 *
 * <p><b>Invariants enforced:</b></p>
 * <ul>
 *   <li>RN-029 — Name: 2–100 characters (enforced by NomeTerreno VO)</li>
 *   <li>RN-030 — Area: 50 m² – 100,000 ha (enforced by AreaTerreno VO)</li>
 *   <li>RN-031 — Soil type: SiBCS/EMBRAPA values only (enforced by enum)</li>
 *   <li>RN-032 — Climate: Köppen-Geiger BR values only (enforced by enum)</li>
 *   <li>RN-033 — pH: 3.0–9.0, default 6.5 when omitted (enforced by pH VO)</li>
 *   <li>RN-034 — Sunlight index: 2–16 h/day when provided (enforced by IndiceIluminosidade VO)</li>
 *   <li>RN-036 — Area cannot be reduced below the sum of its zones' areas</li>
 *   <li>RN-037 — Cannot be deleted if any zone has an active crop cycle</li>
 * </ul>
 */
public class Terreno {

    private TerrenoId id;
    private String agricultorId; // AgricultorId representado como String (UUID)
    private NomeTerreno nome;
    private AreaTerreno area;
    private TipoSoloTerreno tipoSolo;
    private ClimaRegiaoTerreno climaRegiao;
    private Ph ph;
    private IndiceIluminosidade indiceIluminosidade; // nullable — optional field
    private boolean possuiCultivoAtivo;

    private final List<Object> events = new ArrayList<>();

    // -------------------------------------------------------------------------
    // Creation constructor
    // -------------------------------------------------------------------------

    /**
     * Creates a new Terreno with mandatory fields. pH defaults to 6.5 if null.
     * IndiceIluminosidade is optional and may be null.
     */
    public Terreno(
            String agricultorId,
            NomeTerreno nome,
            AreaTerreno area,
            TipoSoloTerreno tipoSolo,
            ClimaRegiaoTerreno climaRegiao,
            Ph ph,
            IndiceIluminosidade indiceIluminosidade
    ) {
        Objects.requireNonNull(agricultorId, "AgricultorId cannot be null");
        Objects.requireNonNull(nome, "NomeTerreno cannot be null");
        Objects.requireNonNull(area, "AreaTerreno cannot be null");
        Objects.requireNonNull(tipoSolo, "TipoSoloTerreno cannot be null");
        Objects.requireNonNull(climaRegiao, "ClimaRegiaoTerreno cannot be null");

        this.id = TerrenoId.novo();
        this.agricultorId = agricultorId;
        this.nome = nome;
        this.area = area;
        this.tipoSolo = tipoSolo;
        this.climaRegiao = climaRegiao;
        this.ph = (ph != null) ? ph : Ph.padrao(); // RN-033: default 6.5
        this.indiceIluminosidade = indiceIluminosidade; // nullable — RN-034
        this.possuiCultivoAtivo = false;

        this.events.add(new TerrenoCriado(this.id, this.agricultorId));
    }

    // -------------------------------------------------------------------------
    // Reconstitution constructor (from persistence)
    // -------------------------------------------------------------------------

    public Terreno(
            TerrenoId id,
            String agricultorId,
            NomeTerreno nome,
            AreaTerreno area,
            TipoSoloTerreno tipoSolo,
            ClimaRegiaoTerreno climaRegiao,
            Ph ph,
            IndiceIluminosidade indiceIluminosidade,
            boolean possuiCultivoAtivo
    ) {
        Objects.requireNonNull(id, "TerrenoId cannot be null");
        Objects.requireNonNull(agricultorId, "AgricultorId cannot be null");
        Objects.requireNonNull(nome, "NomeTerreno cannot be null");
        Objects.requireNonNull(area, "AreaTerreno cannot be null");
        Objects.requireNonNull(tipoSolo, "TipoSoloTerreno cannot be null");
        Objects.requireNonNull(climaRegiao, "ClimaRegiaoTerreno cannot be null");
        Objects.requireNonNull(ph, "pH cannot be null");

        this.id = id;
        this.agricultorId = agricultorId;
        this.nome = nome;
        this.area = area;
        this.tipoSolo = tipoSolo;
        this.climaRegiao = climaRegiao;
        this.ph = ph;
        this.indiceIluminosidade = indiceIluminosidade;
        this.possuiCultivoAtivo = possuiCultivoAtivo;
    }

    // -------------------------------------------------------------------------
    // Domain behaviour — editing (US-08, RN-035, RN-036)
    // -------------------------------------------------------------------------

    /**
     * Updates the land plot name.
     * RN-035: same validation rules as registration apply to editing.
     */
    public void atualizarNome(NomeTerreno novoNome) {
        Objects.requireNonNull(novoNome, "NomeTerreno cannot be null");
        this.nome = novoNome;
    }

    /**
     * Updates the area of the land plot.
     * RN-036: the new area must not be smaller than the total area already
     * occupied by zones. The caller (Application Service) must supply
     * {@code totalAreaZonasM2} — the sum of all zone areas belonging to this Terreno.
     *
     * @param novaArea         the new area to set
     * @param totalAreaZonasM2 the total area currently occupied by all zones
     */
    public void atualizarArea(AreaTerreno novaArea, AreaTerreno totalAreaZonasM2) {
        Objects.requireNonNull(novaArea, "AreaTerreno cannot be null");
        Objects.requireNonNull(totalAreaZonasM2, "Total zone area cannot be null");
        if (!novaArea.isGreaterThanOrEqual(totalAreaZonasM2)) {
            throw new IllegalArgumentException(
                    "Land plot area cannot be reduced below the total area of its zones"
            );
        }
        this.area = novaArea;
    }

    /**
     * Updates the soil type.
     * RN-035: enum membership guarantees valid values.
     */
    public void atualizarTipoSolo(TipoSoloTerreno novoTipoSolo) {
        Objects.requireNonNull(novoTipoSolo, "TipoSoloTerreno cannot be null");
        this.tipoSolo = novoTipoSolo;
    }

    /**
     * Updates the predominant climate.
     * RN-035: enum membership guarantees valid values.
     */
    public void atualizarClimaRegiao(ClimaRegiaoTerreno novoClima) {
        Objects.requireNonNull(novoClima, "ClimaRegiaoTerreno cannot be null");
        this.climaRegiao = novoClima;
    }

    /**
     * Updates the pH value.
     * RN-035: same validation rules (3.0–9.0) enforced by pH VO constructor.
     */
    public void atualizarPH(Ph novoPH) {
        Objects.requireNonNull(novoPH, "pH cannot be null");
        this.ph = novoPH;
    }

    /**
     * Updates (or clears) the sunlight index.
     * Passing null clears the value — the field remains optional after creation.
     */
    public void atualizarIndiceIluminosidade(IndiceIluminosidade novoIndice) {
        this.indiceIluminosidade = novoIndice;
    }

    // -------------------------------------------------------------------------
    // Domain behaviour — deletion guard (US-09, RN-037)
    // -------------------------------------------------------------------------

    /**
     * Signals that this land plot has at least one zone with an active crop cycle.
     * Called by the Application Service after querying the crop subdomain.
     * This flag prevents deletion (RN-037).
     */
    public void marcarComCultivoAtivo() {
        this.possuiCultivoAtivo = true;
    }

    /**
     * Signals that no zone in this land plot has an active crop cycle.
     */
    public void marcarSemCultivoAtivo() {
        this.possuiCultivoAtivo = false;
    }

    /**
     * Guard method checked by Application Service before deletion.
     * RN-037: a Terreno can only be deleted when no zone has an active crop cycle.
     *
     * @throws IllegalStateException if deletion is not allowed
     */
    public void validarPermissaoExclusao() {
        if (possuiCultivoAtivo) {
            throw new IllegalStateException(
                    "Land plot cannot be deleted: at least one zone has an active crop cycle"
            );
        }
    }

    // -------------------------------------------------------------------------
    // Event handling
    // -------------------------------------------------------------------------

    public List<Object> pullEvents() {
        List<Object> snapshot = Collections.unmodifiableList(new ArrayList<>(events));
        events.clear();
        return snapshot;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public TerrenoId getId() { return id; }
    public String getAgricultorId() { return agricultorId; }
    public NomeTerreno getNome() { return nome; }
    public AreaTerreno getArea() { return area; }
    public TipoSoloTerreno getTipoSolo() { return tipoSolo; }
    public ClimaRegiaoTerreno getClimaRegiao() { return climaRegiao; }
    public Ph getPh() { return ph; }
    public IndiceIluminosidade getIndiceIluminosidade() { return indiceIluminosidade; }
    public boolean isPossuiCultivoAtivo() { return possuiCultivoAtivo; }

    // -------------------------------------------------------------------------
    // Domain Event — inner static class (section 2.4 pattern)
    // -------------------------------------------------------------------------

    public static class TerrenoCriado {
        public final TerrenoId terrenoId;
        public final String agricultorId; // AgricultorId representado como String (UUID)

        public TerrenoCriado(TerrenoId terrenoId, String agricultorId) {
            this.terrenoId = terrenoId;
            this.agricultorId = agricultorId;
        }
    }
}
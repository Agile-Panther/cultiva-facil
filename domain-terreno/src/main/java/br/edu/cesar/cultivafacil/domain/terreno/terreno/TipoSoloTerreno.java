package br.edu.cesar.cultivafacil.domain.terreno.terreno;

/**
 * Soil type classification for a land plot.
 * Valid values follow the Brazilian Soil Classification System (SiBCS/EMBRAPA).
 * Invariant (RN-031): value must belong to this accepted set.
 */
public enum TipoSoloTerreno {
    LATOSSOLO,
    ARGISSOLO,
    NEOSSOLO,
    CAMBISSOLO,
    GLEISSOLO,
    NITOSSOLO,
    VERTISSOLO,
    PLINTOSSOLO
}
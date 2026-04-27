package br.edu.cesar.cultivafacil.domain.terreno.terreno;

/**
 * Predominant climate classification for a land plot.
 * Valid values follow the Köppen-Geiger classification for Brazil.
 * Invariant (RN-032): value must belong to this accepted set.
 */
public enum ClimaRegiaoTerreno {
    TROPICAL_UMIDO,
    TROPICAL_SAVANICO,
    TROPICAL_ESTACAO_SECA,
    SEMIARIDO,
    SUBTROPICAL_UMIDO,
    SUBTROPICAL_ALTITUDE,
    SUBTROPICAL_INVERNO_SECO
}
package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

import java.text.Normalizer;

public enum ClimaRegiao {
    TROPICAL_UMIDO,
    TROPICAL_SAVANICO,
    TROPICAL_COM_ESTACAO_SECA_NO_VERAO,
    SEMIARIDO,
    SUBTROPICAL_UMIDO,
    SUBTROPICAL_ALTITUDE,
    SUBTROPICAL;

    public static ClimaRegiao fromString(String value) {
        Validate.notBlank(value, "PROPRIEDADE_INVALIDO");
        String normalizado = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "_")
                .replaceAll("_+", "_")
                .replaceAll("_$", "");
        try {
            return ClimaRegiao.valueOf(normalizado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("PROPRIEDADE_INVALIDO");
        }
    }
}

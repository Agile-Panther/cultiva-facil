package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

import java.text.Normalizer;

public enum TipoSolo {
    LATOSSOLO,
    ARGISSOLO,
    NEOSSOLO,
    CAMBISSOLO,
    GLEISSOLO,
    NITOSSOLO,
    VERTISSOLO,
    PLINTOSSOLO;

    public static TipoSolo fromString(String value) {
        Validate.notBlank(value, "PROPRIEDADE_INVALIDO");
        String normalizado = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .replaceAll("[^A-Z]", "");
        try {
            return TipoSolo.valueOf(normalizado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("PROPRIEDADE_INVALIDO");
        }
    }
}

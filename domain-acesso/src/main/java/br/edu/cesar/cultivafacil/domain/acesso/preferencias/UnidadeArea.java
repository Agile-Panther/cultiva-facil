package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.util.Objects;

public enum UnidadeArea {
    HECTARES,
    METROS_QUADRADOS;

    public static UnidadeArea de(String formato) {
        Objects.requireNonNull(formato, "UnidadeArea nao pode ser nulo");
        if (formato.isBlank()) {
            throw new IllegalArgumentException("UnidadeArea nao pode ser vazio");
        }
        switch (formato.trim().toLowerCase()) {
            case "hectares":
            case "hectares (2 casas)":
            case "ha":
                return HECTARES;
            case "metros quadrados":
            case "m² (2 casas)":
            case "m2":
            case "m²":
                return METROS_QUADRADOS;
            default:
                throw new IllegalArgumentException("FORMATO_AREA_INVALIDO: " + formato);
        }
    }
}

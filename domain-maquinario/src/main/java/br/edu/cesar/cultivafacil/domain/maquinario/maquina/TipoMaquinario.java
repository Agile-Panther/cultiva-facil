package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Objects;

public enum TipoMaquinario {
    TRATOR,
    COLHEITADEIRA,
    PULVERIZADOR,
    PLANTADEIRA,
    IMPLEMENTO_GERAL;

    public static TipoMaquinario de(String valor) {
        Objects.requireNonNull(valor, "TipoMaquinario nao pode ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("TIPO_MAQUINARIO_INVALIDO: valor vazio");
        }
        switch (valor.trim()) {
            case "Trator":
            case "TRATOR":
                return TRATOR;
            case "Colheitadeira":
            case "COLHEITADEIRA":
                return COLHEITADEIRA;
            case "Pulverizador":
            case "PULVERIZADOR":
                return PULVERIZADOR;
            case "Plantadeira":
            case "PLANTADEIRA":
                return PLANTADEIRA;
            case "ImplementoGeral":
            case "IMPLEMENTO_GERAL":
                return IMPLEMENTO_GERAL;
            default:
                throw new IllegalArgumentException("TIPO_MAQUINARIO_INVALIDO: " + valor);
        }
    }
}

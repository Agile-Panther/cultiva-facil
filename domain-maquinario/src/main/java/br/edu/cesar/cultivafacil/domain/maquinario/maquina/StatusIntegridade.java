package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

public enum StatusIntegridade {
    DISPONIVEL,
    EM_MANUTENCAO,
    INOPERANTE;

    public static StatusIntegridade de(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("STATUS_INTEGRIDADE_INVALIDO: valor vazio");
        }
        switch (valor.trim()) {
            case "Disponível":
            case "Disponivel":
            case "DISPONIVEL":
                return DISPONIVEL;
            case "Em Manutenção":
            case "Em Manutencao":
            case "EM_MANUTENCAO":
                return EM_MANUTENCAO;
            case "Inoperante":
            case "INOPERANTE":
                return INOPERANTE;
            default:
                throw new IllegalArgumentException("STATUS_INTEGRIDADE_INVALIDO: " + valor);
        }
    }
}

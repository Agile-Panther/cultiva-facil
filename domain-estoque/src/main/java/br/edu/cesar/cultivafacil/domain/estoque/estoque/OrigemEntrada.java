package br.edu.cesar.cultivafacil.domain.estoque.estoque;

public enum OrigemEntrada {
    COMPRA_CONFIRMADA("Compra Confirmada"),
    DEVOLUCAO_OPERACIONAL("Devolucao Operacional");

    private final String descricao;

    OrigemEntrada(String descricao) {
        this.descricao = descricao;
    }

    public String descricao() {
        return descricao;
    }

    public static OrigemEntrada de(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ESTOQUE_INVALIDO");
        }
        String normalizado = normalizar(valor);
        for (OrigemEntrada origem : values()) {
            if (normalizar(origem.descricao).equals(normalizado) || origem.name().equalsIgnoreCase(normalizado)) {
                return origem;
            }
        }
        throw new IllegalArgumentException("ESTOQUE_INVALIDO");
    }

    private static String normalizar(String valor) {
        return valor.trim()
                .replace("ç", "c")
                .replace("Ç", "C")
                .replace("ã", "a")
                .replace("Ã", "A")
                .replace("í", "i")
                .replace("Í", "I")
                .replace(" ", "_")
                .toUpperCase();
    }
}

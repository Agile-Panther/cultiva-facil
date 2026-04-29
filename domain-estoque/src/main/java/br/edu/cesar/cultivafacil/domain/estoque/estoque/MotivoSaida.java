package br.edu.cesar.cultivafacil.domain.estoque.estoque;

public enum MotivoSaida {
    VENDA("Venda"),
    DOACAO("Doacao"),
    DESCARTE("Descarte"),
    APLICACAO_EM_TALHAO("AplicacaoEmTalhao");

    private final String descricao;

    MotivoSaida(String descricao) {
        this.descricao = descricao;
    }

    public static MotivoSaida de(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("TALHAO_INVALIDO");
        }
        String normalizado = normalizar(valor);
        for (MotivoSaida motivo : values()) {
            if (normalizar(motivo.descricao).equals(normalizado) || motivo.name().equalsIgnoreCase(normalizado)) {
                return motivo;
            }
        }
        throw new IllegalArgumentException("TALHAO_INVALIDO");
    }

    private static String normalizar(String valor) {
        return valor.trim()
                .replace("ç", "c")
                .replace("Ç", "C")
                .replace("ã", "a")
                .replace("Ã", "A")
                .replace("ã", "a")
                .replace("Ã", "A")
                .replace(" ", "_")
                .toUpperCase();
    }
}

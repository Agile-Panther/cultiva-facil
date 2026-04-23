package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

public class IntervaloNaoCumpridoException extends RuntimeException {

    private static final String CODIGO = "INTERVALO_NAO_CUMPRIDO";

    public IntervaloNaoCumpridoException() {
        super("O intervalo de descanso definido para esta cultura ainda nao foi cumprido.");
    }

    public String getCodigo() { return CODIGO; }
}

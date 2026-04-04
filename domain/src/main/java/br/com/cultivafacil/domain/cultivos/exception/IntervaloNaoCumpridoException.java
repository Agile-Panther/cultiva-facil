package br.com.cultivafacil.domain.cultivos.exception;

public class IntervaloNaoCumpridoException extends RuntimeException {

    private final String codigo;

    public IntervaloNaoCumpridoException() {
        super("O intervalo de descanso definido para esta cultura ainda não foi cumprido.");
        this.codigo = "INTERVALO_NAO_CUMPRIDO";
    }

    public String getCodigo() {
        return codigo;
    }
}
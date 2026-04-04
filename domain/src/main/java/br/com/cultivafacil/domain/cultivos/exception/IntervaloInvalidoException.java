package br.com.cultivafacil.domain.cultivos.exception;

public class IntervaloInvalidoException extends RuntimeException {

    private final String codigo;

    public IntervaloInvalidoException() {
        super("O intervalo de descanso deve ser entre 1 e 365 dias.");
        this.codigo = "INTERVALO_INVALIDO";
    }

    public String getCodigo() {
        return codigo;
    }
}
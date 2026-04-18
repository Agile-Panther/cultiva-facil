package br.com.cultivafacil.domain.manejo.exception;

public class SeveridadeInvalidaException extends RuntimeException {
    public SeveridadeInvalidaException(String mensagem) {
        super(mensagem);
    }
    public String getCodigo() {
        return "SEVERIDADE_INVALIDA";
    }
}
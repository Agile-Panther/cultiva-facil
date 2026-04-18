package br.com.cultivafacil.domain.manejo.exception;

public class NivelInfestacaoInvalidoException extends RuntimeException {
    public NivelInfestacaoInvalidoException(String mensagem) {
        super(mensagem);
    }
    public String getCodigo() {
        return "NIVEL_INFESTACAO_INVALIDO";
    }
}
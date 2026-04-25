package br.edu.cesar.cultivafacil.domain.sanidade.foco.exception;

public class NivelInfestacaoInvalidoException extends RuntimeException {
    public NivelInfestacaoInvalidoException(String mensagem) {
        super(mensagem);
    }
    public String getCodigo() {
        return "NIVEL_INFESTACAO_INVALIDO";
    }
}
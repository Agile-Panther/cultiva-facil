package br.edu.cesar.cultivafacil.domain.sanidade.foco.exception;

public class TipoFocoInvalidoException extends RuntimeException {
    public TipoFocoInvalidoException(String mensagem) {
        super(mensagem);
    }
    public String getCodigo() {
        return "TIPO_FOCO_INVALIDO";
    }
}
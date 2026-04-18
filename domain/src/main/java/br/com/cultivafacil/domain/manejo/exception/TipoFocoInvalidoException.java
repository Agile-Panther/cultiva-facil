package br.com.cultivafacil.domain.manejo.exception;

public class TipoFocoInvalidoException extends RuntimeException {
    public TipoFocoInvalidoException(String mensagem) {
        super(mensagem);
    }
    public String getCodigo() {
        return "TIPO_FOCO_INVALIDO";
    }
}
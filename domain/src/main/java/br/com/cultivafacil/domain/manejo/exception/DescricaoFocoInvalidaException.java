package br.com.cultivafacil.domain.manejo.exception;

public class DescricaoFocoInvalidaException extends RuntimeException {
    public DescricaoFocoInvalidaException(String mensagem) {
        super(mensagem);
    }
    public String getCodigo() {
        return "DESCRICAO_FOCO_INVALIDA";
    }
}
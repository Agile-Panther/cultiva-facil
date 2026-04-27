package br.edu.cesar.cultivafacil.domain.insumo.plano;

public class InsumoDomainException extends RuntimeException {

    private final InsumoErroCodigo codigo;

    public InsumoDomainException(InsumoErroCodigo codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public InsumoErroCodigo getCodigo() {
        return codigo;
    }
}
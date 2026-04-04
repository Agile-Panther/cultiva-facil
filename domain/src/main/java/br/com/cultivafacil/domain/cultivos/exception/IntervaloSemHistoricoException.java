package br.com.cultivafacil.domain.cultivos.exception;

public class IntervaloSemHistoricoException extends RuntimeException {

    private final String codigo;

    public IntervaloSemHistoricoException() {
        super("Não é possível definir intervalo de descanso sem histórico de ciclo encerrado para esta cultura.");
        this.codigo = "INTERVALO_SEM_HISTORICO";
    }

    public String getCodigo() {
        return codigo;
    }
}
package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

public class IntervaloSemHistoricoException extends RuntimeException {

    private static final String CODIGO = "INTERVALO_SEM_HISTORICO";

    public IntervaloSemHistoricoException() {
        super("Nao e possivel definir intervalo de descanso sem historico de ciclo encerrado para esta cultura.");
    }

    public String getCodigo() { return CODIGO; }
}

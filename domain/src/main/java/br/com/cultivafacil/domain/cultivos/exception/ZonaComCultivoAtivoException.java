package br.com.cultivafacil.domain.cultivos.exception;

public class ZonaComCultivoAtivoException extends RuntimeException {

    private final String codigo;

    public ZonaComCultivoAtivoException() {
        super("Zona já possui um cultivo ativo. Encerre o ciclo atual antes de vincular uma nova cultura.");
        this.codigo = "ZONA_OCUPADA";
    }

    public String getCodigo() {
        return codigo;
    }
}
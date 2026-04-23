package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

public class ZonaComCultivoAtivoException extends RuntimeException {

    private static final String CODIGO = "ZONA_COM_CULTIVO_ATIVO";

    public ZonaComCultivoAtivoException() {
        super("Zona ja possui um cultivo ativo. Encerre o ciclo atual antes de vincular uma nova cultura.");
    }

    public String getCodigo() { return CODIGO; }
}

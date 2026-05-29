package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public class DispensaDescanso {

    private final JustificativaDispensa justificativa;
    private final StatusDispensa status;
    private final LocalDate concedidaEm;

    public DispensaDescanso(JustificativaDispensa justificativa) {
        Validate.notNull(justificativa, "justificativa e obrigatoria");
        this.justificativa = justificativa;
        this.status = StatusDispensa.CONCEDIDA;
        this.concedidaEm = LocalDate.now();
    }

    public JustificativaDispensa getJustificativa() { return justificativa; }
    public StatusDispensa getStatus() { return status; }
    public LocalDate getConcedidaEm() { return concedidaEm; }
}

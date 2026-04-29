package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public class CancelamentoCiclo {

    private final JustificativaCancelamento justificativa;
    private final LocalDate canceladoEm;

    public CancelamentoCiclo(JustificativaCancelamento justificativa) {
        Validate.notNull(justificativa, "justificativa e obrigatoria");
        this.justificativa = justificativa;
        this.canceladoEm = LocalDate.now();
    }

    public JustificativaCancelamento getJustificativa() { return justificativa; }
    public LocalDate getCanceladoEm() { return canceladoEm; }
}

package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public class VersaoIntervaloDescanso {

    private final DiasDescanso dias;
    private final LocalDate definidoEm;
    private final LocalDate dataReferencia;

    public VersaoIntervaloDescanso(DiasDescanso dias, LocalDate dataReferencia) {
        Validate.notNull(dias, "dias e obrigatorio");
        Validate.notNull(dataReferencia, "dataReferencia e obrigatoria");
        this.dias = dias;
        this.definidoEm = LocalDate.now();
        this.dataReferencia = dataReferencia;
    }

    public boolean foiCumprido(LocalDate data) {
        return !data.isBefore(dataReferencia.plusDays(dias.getValor()));
    }

    public DiasDescanso getDias() { return dias; }
    public LocalDate getDefinidoEm() { return definidoEm; }
    public LocalDate getDataReferencia() { return dataReferencia; }
}

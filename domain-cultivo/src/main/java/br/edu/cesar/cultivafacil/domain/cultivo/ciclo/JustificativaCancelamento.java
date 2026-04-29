package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.apache.commons.lang3.Validate;

public final class JustificativaCancelamento {

    private final String valor;

    public JustificativaCancelamento(String valor) {
        Validate.notNull(valor, "JUSTIFICATIVA_INVALIDA: justificativa nao pode ser nula");
        String trimmed = valor.trim();
        Validate.isTrue(trimmed.length() >= 20 && trimmed.length() <= 500,
                "JUSTIFICATIVA_INVALIDA: justificativa deve ter entre 20 e 500 caracteres");
        this.valor = trimmed;
    }

    public String getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JustificativaCancelamento that)) return false;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() { return valor.hashCode(); }
}

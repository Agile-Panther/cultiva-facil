package br.edu.cesar.cultivafacil.domain.insumo.plano;

import java.time.YearMonth;
import java.util.Objects;

public class MesReferencia {

    private final YearMonth valor;

    public MesReferencia(YearMonth valor) {
        if (valor == null) {
            throw new InsumoDomainException(InsumoErroCodigo.MES_REFERENCIA_PASSADO,
                    "Mes de referencia nao pode ser nulo");
        }
        if (!valor.isAfter(YearMonth.now())) {
            throw new InsumoDomainException(InsumoErroCodigo.MES_REFERENCIA_PASSADO,
                    "Mes de referencia deve ser futuro");
        }
        this.valor = valor;
    }

    public YearMonth getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MesReferencia)) return false;
        return Objects.equals(valor, ((MesReferencia) o).valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
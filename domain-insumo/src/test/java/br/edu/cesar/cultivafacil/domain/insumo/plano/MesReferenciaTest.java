package br.edu.cesar.cultivafacil.domain.insumo.plano;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MesReferenciaTest {

    @Test
    void deveRejeitarMesDeReferenciaPassado() {
        YearMonth mesPassado = YearMonth.now().minusMonths(1);

        assertThrows(InsumoDomainException.class, () -> new MesReferencia(mesPassado));
    }

    @Test
    void deveRejeitarMesDeReferenciaComMaisDeUmAnoPAssado() {
        YearMonth mesAntepassado = YearMonth.now().minusYears(1);

        assertThrows(InsumoDomainException.class, () -> new MesReferencia(mesAntepassado));
    }

    @Test
    void deveRejeitarMesDeReferenciaAtual() {
        YearMonth mesAtual = YearMonth.now();

        assertThrows(InsumoDomainException.class, () -> new MesReferencia(mesAtual));
    }

    @Test
    void deveAceitarMesDeReferenciaFuturo() {
        YearMonth mesFuturo = YearMonth.now().plusMonths(3);

        MesReferencia mes = new MesReferencia(mesFuturo);

        assertThat(mes.getValor()).isEqualTo(mesFuturo);
    }

    @Test
    void deveRejeitarMesDeReferenciaPassadoMensagemCorreta() {
        YearMonth mesPassado = YearMonth.now().minusMonths(1);

        assertThatThrownBy(() -> new MesReferencia(mesPassado))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Mes de referencia deve ser futuro");
    }

    @Test
    void deveTerEqualsCorretoPorValor() {
        YearMonth mesFuturo = YearMonth.now().plusMonths(1);
        MesReferencia mes1 = new MesReferencia(mesFuturo);
        MesReferencia mes2 = new MesReferencia(mesFuturo);

        assertThat(mes1).isEqualTo(mes2);
    }
}
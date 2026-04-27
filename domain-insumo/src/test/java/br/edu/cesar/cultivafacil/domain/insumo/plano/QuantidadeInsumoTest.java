package br.edu.cesar.cultivafacil.domain.insumo.plano;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuantidadeInsumoTest {

    @Test
    void deveRejeitarQuantidadeZero() {
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(0));
    }

    @Test
    void deveRejeitarQuantidadeNegativa() {
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(-5));
    }

    @Test
    void deveRejeitarQuantidadeFracionalNegativa() {
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(-0.01));
    }

    @Test
    void deveAceitarQuantidadePositiva() {
        QuantidadeInsumo quantidade = new QuantidadeInsumo(10);

        assertThat(quantidade.getValor()).isEqualTo(10);
    }

    @Test
    void deveAceitarQuantidadeFracionalPositiva() {
        QuantidadeInsumo quantidade = new QuantidadeInsumo(0.5);

        assertThat(quantidade.getValor()).isEqualTo(0.5);
    }

    @Test
    void deveRejeitarQuantidadeZeroComMensagemCorreta() {
        assertThatThrownBy(() -> new QuantidadeInsumo(0))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Quantidade de insumo deve ser positiva e superior a zero");
    }
}
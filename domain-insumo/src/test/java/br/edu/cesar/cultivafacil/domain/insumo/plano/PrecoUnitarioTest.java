package br.edu.cesar.cultivafacil.domain.insumo.plano;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrecoUnitarioTest {

    @Test
    void deveRejeitarPrecoZero() {
        assertThrows(InsumoDomainException.class, () -> new PrecoUnitario(BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarPrecoNegativo() {
        assertThrows(InsumoDomainException.class, () -> new PrecoUnitario(new BigDecimal("-1.00")));
    }

    @Test
    void deveAceitarPrecoPositivo() {
        PrecoUnitario preco = new PrecoUnitario(new BigDecimal("10.50"));

        assertThat(preco.getValor()).isEqualByComparingTo(new BigDecimal("10.50"));
    }

    @Test
    void deveAceitarPrecoFracionalPositivo() {
        PrecoUnitario preco = new PrecoUnitario(new BigDecimal("0.01"));

        assertThat(preco.getValor()).isEqualByComparingTo(new BigDecimal("0.01"));
    }

    @Test
    void deveRejeitarPrecoZeroComMensagemCorreta() {
        assertThatThrownBy(() -> new PrecoUnitario(BigDecimal.ZERO))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Preco unitario deve ser positivo e superior a zero");
    }
}
package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class QuantidadePlantadaTest {

    @Nested
    @DisplayName("RN-044 — Quantidade plantada obrigatória e positiva")
    class ValidacaoValorTest {

        @Test
        @DisplayName("deve criar com valor positivo e 2 casas decimais")
        void deveCriarComValorPositivo() {
            QuantidadePlantada qp = new QuantidadePlantada(new BigDecimal("10.50"), UnidadeMedidaCiclo.KG);
            assertEquals(new BigDecimal("10.50"), qp.getValor());
            assertEquals(UnidadeMedidaCiclo.KG, qp.getUnidade());
        }

        @Test
        @DisplayName("deve rejeitar valor zero")
        void deveRejeitarValorZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> new QuantidadePlantada(BigDecimal.ZERO, UnidadeMedidaCiclo.KG));
        }

        @Test
        @DisplayName("deve rejeitar valor negativo")
        void deveRejeitarValorNegativo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new QuantidadePlantada(new BigDecimal("-5.00"), UnidadeMedidaCiclo.G));
        }

        @Test
        @DisplayName("deve rejeitar mais de 2 casas decimais")
        void deveRejeitarMaisDe2CasasDecimais() {
            assertThrows(IllegalArgumentException.class,
                    () -> new QuantidadePlantada(new BigDecimal("10.567"), UnidadeMedidaCiclo.KG));
        }

        @Test
        @DisplayName("deve rejeitar valor nulo")
        void deveRejeitarValorNulo() {
            assertThrows(NullPointerException.class,
                    () -> new QuantidadePlantada(null, UnidadeMedidaCiclo.KG));
        }

        @Test
        @DisplayName("deve rejeitar unidade nula")
        void deveRejeitarUnidadeNula() {
            assertThrows(NullPointerException.class,
                    () -> new QuantidadePlantada(new BigDecimal("10.00"), null));
        }
    }

    @Nested
    @DisplayName("RN-045 — Unidades de medida aceitas")
    class UnidadeMedidaTest {

        @Test
        @DisplayName("deve aceitar KG")
        void deveAceitarKg() {
            QuantidadePlantada qp = new QuantidadePlantada(new BigDecimal("1.00"), UnidadeMedidaCiclo.KG);
            assertEquals(UnidadeMedidaCiclo.KG, qp.getUnidade());
        }

        @Test
        @DisplayName("deve aceitar G")
        void deveAceitarG() {
            QuantidadePlantada qp = new QuantidadePlantada(new BigDecimal("500.00"), UnidadeMedidaCiclo.G);
            assertEquals(UnidadeMedidaCiclo.G, qp.getUnidade());
        }

        @Test
        @DisplayName("deve aceitar UNIDADES")
        void deveAceitarUnidades() {
            QuantidadePlantada qp = new QuantidadePlantada(new BigDecimal("20.00"), UnidadeMedidaCiclo.UNIDADES);
            assertEquals(UnidadeMedidaCiclo.UNIDADES, qp.getUnidade());
        }
    }

    @Test
    @DisplayName("deve ser igual quando mesmo valor e unidade")
    void deveSerIgualQuandoMesmoValorEUnidade() {
        QuantidadePlantada qp1 = new QuantidadePlantada(new BigDecimal("10.50"), UnidadeMedidaCiclo.KG);
        QuantidadePlantada qp2 = new QuantidadePlantada(new BigDecimal("10.50"), UnidadeMedidaCiclo.KG);
        assertEquals(qp1, qp2);
    }
}

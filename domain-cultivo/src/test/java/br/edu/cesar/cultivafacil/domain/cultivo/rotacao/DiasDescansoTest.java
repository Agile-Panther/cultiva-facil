package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiasDescansoTest {

    @Test
    @DisplayName("RN-052: deve criar DiasDescanso com valor valido")
    void deveCriarDiasDescansoComValorValido() {
        DiasDescanso dias = new DiasDescanso(30);
        assertEquals(30, dias.getDias());
    }

    @Test
    @DisplayName("RN-052: deve aceitar limite inferior (1 dia)")
    void deveAceitarLimiteInferior() {
        DiasDescanso dias = new DiasDescanso(1);
        assertEquals(1, dias.getDias());
    }

    @Test
    @DisplayName("RN-052: deve aceitar limite superior (365 dias)")
    void deveAceitarLimiteSuperior() {
        DiasDescanso dias = new DiasDescanso(365);
        assertEquals(365, dias.getDias());
    }

    @Test
    @DisplayName("RN-052: deve rejeitar intervalo abaixo do minimo (0)")
    void deveRejeitarIntervaloAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(0));
    }

    @Test
    @DisplayName("RN-052: deve rejeitar intervalo negativo")
    void deveRejeitarIntervaloNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(-1));
    }

    @Test
    @DisplayName("RN-052: deve rejeitar intervalo acima do maximo (366)")
    void deveRejeitarIntervaloAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(366));
    }

    @Test
    @DisplayName("RN-052: deve rejeitar intervalo muito acima do maximo")
    void deveRejeitarIntervaloMuitoAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> new DiasDescanso(400));
    }

    @Test
    @DisplayName("deve ser igual quando mesmo valor")
    void deveSerIgualQuandoMesmoValor() {
        DiasDescanso dias1 = new DiasDescanso(30);
        DiasDescanso dias2 = new DiasDescanso(30);
        assertEquals(dias1, dias2);
        assertEquals(dias1.hashCode(), dias2.hashCode());
    }
}

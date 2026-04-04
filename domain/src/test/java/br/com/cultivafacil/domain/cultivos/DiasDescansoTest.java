package br.com.cultivafacil.domain.cultivos;

import br.com.cultivafacil.domain.cultivos.exception.IntervaloInvalidoException;
import br.com.cultivafacil.domain.cultivos.vo.DiasDescanso;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DiasDescansoTest {

    // Rastreabilidade: US-15 / RN-02a / cenário ✗ INTERVALO_INVALIDO
    @Test
    @DisplayName("RN-02a: deve rejeitar intervalo de descanso abaixo do mínimo")
    void deveRejeitarIntervaloAbaixoDoMinimo() {
        assertThrows(
                IntervaloInvalidoException.class,
                () -> new DiasDescanso(0)
        );
    }

    // Rastreabilidade: US-15 / RN-02b / cenário ✗ INTERVALO_INVALIDO
    @Test
    @DisplayName("RN-02b: deve rejeitar intervalo de descanso acima do máximo")
    void deveRejeitarIntervaloAcimaDoMaximo() {
        assertThrows(
                IntervaloInvalidoException.class,
                () -> new DiasDescanso(400)
        );
    }
}
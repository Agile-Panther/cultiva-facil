package br.edu.cesar.cultivafacil.domain.clima;

import br.edu.cesar.cultivafacil.domain.clima.limite.TemperaturaLimite;
import br.edu.cesar.cultivafacil.domain.clima.limite.exception.TemperaturaLimiteInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperaturaLimiteTest {

    // Rastreabilidade: F-17 / RN-01 / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar TemperaturaLimite Válida")
    void deveCriarTemperaturaLimiteValida() {
        // Arrange
        double valorMin = 10.0;
        double valorMax = 30.0;

        // Act
        TemperaturaLimite temperaturaLimite = new TemperaturaLimite(valorMin, valorMax);

        // Assert
        assertNotNull(temperaturaLimite);
        assertEquals(valorMin, temperaturaLimite.getValorMin());
        assertEquals(valorMax, temperaturaLimite.getValorMax());
    }

    // Rastreabilidade: F-17 / RN-01 / cenário de falha com temperatura mínima maior que máxima
    @Test
    @DisplayName("Não Deve Criar TemperaturaLimite com Mínima Maior que Máxima")
    void naoDeveCriarTemperaturaLimiteComMinimaMaiorQueMaxima() {
        // Arrange
        double valorMin = 30.0;
        double valorMax = 10.0;

        // Act & Assert
        var exception = assertThrows(TemperaturaLimiteInvalidaException.class, () -> {
            new TemperaturaLimite(valorMin, valorMax);
        });
        assertEquals("A temperatura mínima não pode ser maior que a máxima.", exception.getMessage());
    }

    // Rastreabilidade: F-17 / RN-01 / cenário de falha com temperatura fora do limite realista
    @Test
    @DisplayName("Não Deve Criar TemperaturaLimite Fora do Limite Realista")
    void naoDeveCriarTemperaturaLimiteForaDoLimiteRealista() {
        // Arrange
        double valorMinInvalido = -100.0;
        double valorMaxValido = 30.0;

        // Act & Assert
        var exception = assertThrows(TemperaturaLimiteInvalidaException.class, () -> {
            new TemperaturaLimite(valorMinInvalido, valorMaxValido);
        });
        assertEquals("A temperatura deve estar entre -50 e 60 graus Celsius.", exception.getMessage());
    }
}


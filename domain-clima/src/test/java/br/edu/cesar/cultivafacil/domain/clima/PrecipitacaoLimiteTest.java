package br.edu.cesar.cultivafacil.domain.clima;

import br.com.cultivafacil.domain.clima.exception.PrecipitacaoLimiteInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrecipitacaoLimiteTest {

    // Rastreabilidade: F-17 / RN-02 / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar PrecipitacaoLimite Válida")
    void deveCriarPrecipitacaoLimiteValida() {
        // Arrange
        double valor = 50.0;

        // Act
        PrecipitacaoLimite precipitacaoLimite = new PrecipitacaoLimite(valor);

        // Assert
        assertNotNull(precipitacaoLimite);
        assertEquals(valor, precipitacaoLimite.getValor());
    }

    // Rastreabilidade: F-17 / RN-02 / cenário de falha com precipitação negativa
    @Test
    @DisplayName("Não Deve Criar PrecipitacaoLimite com Valor Negativo")
    void naoDeveCriarPrecipitacaoLimiteComValorNegativo() {
        // Arrange
        double valorInvalido = -10.0;

        // Act & Assert
        var exception = assertThrows(PrecipitacaoLimiteInvalidaException.class, () -> {
            new PrecipitacaoLimite(valorInvalido);
        });
        assertEquals("A precipitação não pode ser negativa.", exception.getMessage());
    }
}


package br.edu.cesar.cultivafacil.domain.clima;

import br.com.cultivafacil.domain.clima.exception.NecessidadeHidricaInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NecessidadeHidricaTest {

    // Rastreabilidade: F-17 / RN-03 / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar NecessidadeHidrica Válida")
    void deveCriarNecessidadeHidricaValida() {
        // Arrange
        double valor = 25.0;

        // Act
        NecessidadeHidrica necessidadeHidrica = new NecessidadeHidrica(valor);

        // Assert
        assertNotNull(necessidadeHidrica);
        assertEquals(valor, necessidadeHidrica.getValor());
    }

    // Rastreabilidade: F-17 / RN-03 / cenário de falha com necessidade hídrica negativa
    @Test
    @DisplayName("Não Deve Criar NecessidadeHidrica com Valor Negativo")
    void naoDeveCriarNecessidadeHidricaComValorNegativo() {
        // Arrange
        double valorInvalido = -5.0;

        // Act & Assert
        var exception = assertThrows(NecessidadeHidricaInvalidaException.class, () -> {
            new NecessidadeHidrica(valorInvalido);
        });
        assertEquals("A necessidade hídrica não pode ser negativa.", exception.getMessage());
    }
}


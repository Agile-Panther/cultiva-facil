package br.edu.cesar.cultivafacil.domain.clima;

import br.com.cultivafacil.domain.clima.exception.JanelaObservacaoInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JanelaObservacaoTest {

    // Rastreabilidade: F-17 / RN-04 / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar JanelaObservacao Válida")
    void deveCriarJanelaObservacaoValida() {
        // Arrange
        int dias = 7;

        // Act
        JanelaObservacao janelaObservacao = new JanelaObservacao(dias);

        // Assert
        assertNotNull(janelaObservacao);
        assertEquals(dias, janelaObservacao.getDias());
    }

    // Rastreabilidade: F-17 / RN-04 / cenário de falha com janela de observação não positiva
    @Test
    @DisplayName("Não Deve Criar JanelaObservacao com Dias Não Positivos")
    void naoDeveCriarJanelaObservacaoComDiasNaoPositivos() {
        // Arrange
        int diasInvalidos = 0;

        // Act & Assert
        var exception = assertThrows(JanelaObservacaoInvalidaException.class, () -> {
            new JanelaObservacao(diasInvalidos);
        });
        assertEquals("A janela de observação deve ser de pelo menos 1 dia.", exception.getMessage());
    }
}


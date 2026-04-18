package br.com.cultivafacil.domain.clima.model;

import br.com.cultivafacil.domain.clima.vo.JanelaObservacao;
import br.com.cultivafacil.domain.clima.vo.LimiteClimaticoId;
import br.com.cultivafacil.domain.clima.vo.PrecipitacaoLimite;
import br.com.cultivafacil.domain.clima.vo.TemperaturaLimite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimiteClimaticoTest {

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar LimiteClimatico Novo e Emitir Evento")
    void deveCriarLimiteClimaticoNovo() {
        // Arrange
        TemperaturaLimite temperatura = new TemperaturaLimite(15, 30);
        PrecipitacaoLimite precipitacao = new PrecipitacaoLimite(20);
        JanelaObservacao janela = new JanelaObservacao(7);

        // Act
        LimiteClimatico limite = new LimiteClimatico(temperatura, precipitacao, janela);

        // Assert
        assertNotNull(limite);
        assertNotNull(limite.getId());
        assertEquals(temperatura, limite.getTemperatura());
        assertEquals(precipitacao, limite.getPrecipitacao());
        assertEquals(janela, limite.getJanelaObservacao());
        // Teste de evento seria melhor com um mock de event publisher,
        // mas por simplicidade vamos apenas verificar se a lista de eventos não está vazia.
        assertFalse(limite.getDomainEvents().isEmpty());
        assertEquals(1, limite.getDomainEvents().size());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de reconstituição
    @Test
    @DisplayName("Deve Reconstituir LimiteClimatico a partir do ID")
    void deveReconstituirLimiteClimatico() {
        // Arrange
        LimiteClimaticoId id = LimiteClimaticoId.novo();
        TemperaturaLimite temperatura = new TemperaturaLimite(15, 30);
        PrecipitacaoLimite precipitacao = new PrecipitacaoLimite(20);
        JanelaObservacao janela = new JanelaObservacao(7);

        // Act
        LimiteClimatico limite = new LimiteClimatico(id, temperatura, precipitacao, janela);

        // Assert
        assertNotNull(limite);
        assertEquals(id, limite.getId());
        assertEquals(temperatura, limite.getTemperatura());
        assertEquals(precipitacao, limite.getPrecipitacao());
        assertEquals(janela, limite.getJanelaObservacao());
        assertTrue(limite.getDomainEvents().isEmpty());
    }
}


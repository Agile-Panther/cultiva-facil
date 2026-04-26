package br.edu.cesar.cultivafacil.domain.clima;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LimiteClimaticoIdTest {

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar LimiteClimaticoId Novo")
    void deveCriarLimiteClimaticoIdNovo() {
        // Act
        LimiteClimaticoId id = LimiteClimaticoId.novo();

        // Assert
        assertNotNull(id);
        assertNotNull(id.getValue());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com UUID específico
    @Test
    @DisplayName("Deve Criar LimiteClimaticoId com UUID Específico")
    void deveCriarLimiteClimaticoIdComUuidEspecifico() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        LimiteClimaticoId id = new LimiteClimaticoId(uuid);

        // Assert
        assertNotNull(id);
        assertEquals(uuid, id.getValue());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de falha com UUID nulo
    @Test
    @DisplayName("Não Deve Criar LimiteClimaticoId com UUID Nulo")
    void naoDeveCriarLimiteClimaticoIdComUuidNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new LimiteClimaticoId(null);
        });
    }
}


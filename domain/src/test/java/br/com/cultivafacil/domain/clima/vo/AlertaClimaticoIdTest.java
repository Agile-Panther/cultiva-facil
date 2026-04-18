package br.com.cultivafacil.domain.clima.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlertaClimaticoIdTest {

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar AlertaClimaticoId Novo")
    void deveCriarAlertaClimaticoIdNovo() {
        // Act
        AlertaClimaticoId id = AlertaClimaticoId.novo();

        // Assert
        assertNotNull(id);
        assertNotNull(id.getValue());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com UUID específico
    @Test
    @DisplayName("Deve Criar AlertaClimaticoId com UUID Específico")
    void deveCriarAlertaClimaticoIdComUuidEspecifico() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        AlertaClimaticoId id = new AlertaClimaticoId(uuid);

        // Assert
        assertNotNull(id);
        assertEquals(uuid, id.getValue());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de falha com UUID nulo
    @Test
    @DisplayName("Não Deve Criar AlertaClimaticoId com UUID Nulo")
    void naoDeveCriarAlertaClimaticoIdComUuidNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new AlertaClimaticoId(null);
        });
    }
}


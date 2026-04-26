package br.edu.cesar.cultivafacil.domain.clima;

import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacaoId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlertaIrrigacaoIdTest {

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar AlertaIrrigacaoId Novo")
    void deveCriarAlertaIrrigacaoIdNovo() {
        // Act
        AlertaIrrigacaoId id = AlertaIrrigacaoId.novo();

        // Assert
        assertNotNull(id);
        assertNotNull(id.getValue());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com UUID específico
    @Test
    @DisplayName("Deve Criar AlertaIrrigacaoId com UUID Específico")
    void deveCriarAlertaIrrigacaoIdComUuidEspecifico() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        AlertaIrrigacaoId id = new AlertaIrrigacaoId(uuid);

        // Assert
        assertNotNull(id);
        assertEquals(uuid, id.getValue());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de falha com UUID nulo
    @Test
    @DisplayName("Não Deve Criar AlertaIrrigacaoId com UUID Nulo")
    void naoDeveCriarAlertaIrrigacaoIdComUuidNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new AlertaIrrigacaoId(null);
        });
    }
}


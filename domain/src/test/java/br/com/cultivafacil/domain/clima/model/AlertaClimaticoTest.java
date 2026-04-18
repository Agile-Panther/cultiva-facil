package br.com.cultivafacil.domain.clima.model;

import br.com.cultivafacil.domain.clima.vo.AlertaClimaticoId;
import br.com.cultivafacil.domain.clima.vo.LimiteClimaticoId;
import br.com.cultivafacil.domain.clima.vo.TipoAlerta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlertaClimaticoTest {

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar AlertaClimatico Novo e Emitir Evento")
    void deveCriarAlertaClimaticoNovo() {
        // Arrange
        LimiteClimaticoId limiteClimaticoId = LimiteClimaticoId.novo();
        TipoAlerta tipoAlerta = TipoAlerta.TEMPERATURA;
        String mensagem = "Alerta de temperatura alta!";

        // Act
        AlertaClimatico alerta = new AlertaClimatico(limiteClimaticoId, tipoAlerta, mensagem);

        // Assert
        assertNotNull(alerta);
        assertNotNull(alerta.getId());
        assertEquals(limiteClimaticoId, alerta.getLimiteClimaticoId());
        assertEquals(tipoAlerta, alerta.getTipoAlerta());
        assertEquals(mensagem, alerta.getMensagem());
        assertNotNull(alerta.getDataHora());
        assertFalse(alerta.getDomainEvents().isEmpty());
        assertEquals(1, alerta.getDomainEvents().size());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de reconstituição
    @Test
    @DisplayName("Deve Reconstituir AlertaClimatico a partir do ID")
    void deveReconstituirAlertaClimatico() {
        // Arrange
        AlertaClimaticoId id = AlertaClimaticoId.novo();
        LimiteClimaticoId limiteClimaticoId = LimiteClimaticoId.novo();
        TipoAlerta tipoAlerta = TipoAlerta.PRECIPITACAO;
        String mensagem = "Alerta de baixa precipitação!";
        LocalDateTime dataHora = LocalDateTime.now();

        // Act
        AlertaClimatico alerta = new AlertaClimatico(id, limiteClimaticoId, tipoAlerta, mensagem, dataHora);

        // Assert
        assertNotNull(alerta);
        assertEquals(id, alerta.getId());
        assertEquals(limiteClimaticoId, alerta.getLimiteClimaticoId());
        assertEquals(tipoAlerta, alerta.getTipoAlerta());
        assertEquals(mensagem, alerta.getMensagem());
        assertEquals(dataHora, alerta.getDataHora());
        assertTrue(alerta.getDomainEvents().isEmpty());
    }
}


package br.edu.cesar.cultivafacil.domain.clima;

import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacao;
import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacaoId;
import br.edu.cesar.cultivafacil.domain.clima.limite.NecessidadeHidrica;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlertaIrrigacaoTest {

    // Rastreabilidade: F-17 / RN-XX / cenário de criação com sucesso
    @Test
    @DisplayName("Deve Criar AlertaIrrigacao Novo e Emitir Evento")
    void deveCriarAlertaIrrigacaoNovo() {
        // Arrange
        NecessidadeHidrica necessidadeHidrica = new NecessidadeHidrica(30);
        String mensagem = "Necessidade hídrica atingiu o limite!";

        // Act
        AlertaIrrigacao alerta = new AlertaIrrigacao(necessidadeHidrica, mensagem);

        // Assert
        assertNotNull(alerta);
        assertNotNull(alerta.getId());
        assertEquals(necessidadeHidrica, alerta.getNecessidadeHidrica());
        assertEquals(mensagem, alerta.getMensagem());
        assertNotNull(alerta.getDataHora());
        assertFalse(alerta.getDomainEvents().isEmpty());
        assertEquals(1, alerta.getDomainEvents().size());
    }

    // Rastreabilidade: F-17 / RN-XX / cenário de reconstituição
    @Test
    @DisplayName("Deve Reconstituir AlertaIrrigacao a partir do ID")
    void deveReconstituirAlertaIrrigacao() {
        // Arrange
        AlertaIrrigacaoId id = AlertaIrrigacaoId.novo();
        NecessidadeHidrica necessidadeHidrica = new NecessidadeHidrica(30);
        String mensagem = "Necessidade hídrica atingiu o limite!";
        LocalDateTime dataHora = LocalDateTime.now();

        // Act
        AlertaIrrigacao alerta = new AlertaIrrigacao(id, necessidadeHidrica, mensagem, dataHora);

        // Assert
        assertNotNull(alerta);
        assertEquals(id, alerta.getId());
        assertEquals(necessidadeHidrica, alerta.getNecessidadeHidrica());
        assertEquals(mensagem, alerta.getMensagem());
        assertEquals(dataHora, alerta.getDataHora());
        assertTrue(alerta.getDomainEvents().isEmpty());
    }
}


package br.com.cultivafacil.domain.cultivos;

import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;
import br.com.cultivafacil.domain.cultivos.model.CicloAgricola;
import br.com.cultivafacil.domain.cultivos.model.Zona;
import br.com.cultivafacil.domain.cultivos.vo.StatusZona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZonaTest {

    // Rastreabilidade: US-12 / RN-01 / cenário ✗ ZONA_OCUPADA
    @Test
    @DisplayName("RN-01: deve rejeitar vínculo de cultura em Zona com cultivo ativo")
    void deveRejeitarSegundoCultivoAtivo() {
        // Arrange
        Zona zona = new Zona();
        zona.vincularCultura("Milho");

        // Act & Assert
        assertThrows(
                ZonaComCultivoAtivoException.class,
                () -> zona.vincularCultura("Feijão")
        );
    }

    // Rastreabilidade: US-12 / fluxo positivo
    @Test
    @DisplayName("deve mudar situacao da Zona para ATIVA ao vincular cultura")
    void deveAtivarZonaAoVincularCultura() {
        // Arrange
        Zona zona = new Zona();

        // Act
        zona.vincularCultura("Milho");

        // Assert
        assertEquals(StatusZona.ATIVA, zona.getSituacao());
    }

    // Rastreabilidade: US-12 / fluxo positivo
    @Test
    @DisplayName("deve registrar ciclo agricola ao vincular cultura")
    void deveRegistrarCicloAoVincularCultura() {
        // Arrange
        Zona zona = new Zona();

        // Act
        zona.vincularCultura("Milho");

        // Assert
        assertEquals(1, zona.getCiclos().size());
        assertEquals("Milho", zona.getCiclos().get(0).getNomeCultura());
    }
}
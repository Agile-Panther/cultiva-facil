package br.com.cultivafacil.domain.manejo.vo;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ZonaIdTest {

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso
    @Test
    void deveCriarZonaIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        ZonaId zonaId = new ZonaId(id);

        // Assert
        assertNotNull(zonaId);
        assertEquals(id, zonaId.getValor());
    }

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso (novo)
    @Test
    void deveCriarNovaZonaId() {
        // Act
        ZonaId zonaId = ZonaId.novo();

        // Assert
        assertNotNull(zonaId);
        assertNotNull(zonaId.getValor());
    }
}


package br.com.cultivafacil.domain.manejo.vo;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CicloAgricolaIdTest {

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso
    @Test
    void deveCriarCicloAgricolaIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        CicloAgricolaId cicloId = new CicloAgricolaId(id);

        // Assert
        assertNotNull(cicloId);
        assertEquals(id, cicloId.getValor());
    }

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso (novo)
    @Test
    void deveCriarNovoCicloAgricolaId() {
        // Act
        CicloAgricolaId cicloId = CicloAgricolaId.novo();

        // Assert
        assertNotNull(cicloId);
        assertNotNull(cicloId.getValor());
    }
}


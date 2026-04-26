package br.edu.cesar.cultivafacil.domain.sanidade;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TalhaoIdTest {

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso
    @Test
    void deveCriarZonaIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        TalhaoId zonaId = new TalhaoId(id);

        // Assert
        assertNotNull(zonaId);
        assertEquals(id, zonaId.getValor());
    }

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso (novo)
    @Test
    void deveCriarNovaZonaId() {
        // Act
        TalhaoId zonaId = TalhaoId.novo();

        // Assert
        assertNotNull(zonaId);
        assertNotNull(zonaId.getValor());
    }
}


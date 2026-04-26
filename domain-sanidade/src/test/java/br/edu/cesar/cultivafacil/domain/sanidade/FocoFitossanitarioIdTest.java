package br.edu.cesar.cultivafacil.domain.sanidade;

import br.edu.cesar.cultivafacil.domain.sanidade.foco.FocoFitossanitarioId;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class FocoFitossanitarioIdTest {

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso
    @Test
    void deveCriarFocoFitossanitarioIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        FocoFitossanitarioId focoId = new FocoFitossanitarioId(id);

        // Assert
        assertNotNull(focoId);
        assertEquals(id, focoId.getValor());
    }

    // Rastreabilidade: F-10 / RN-XX / cenário de sucesso (novo)
    @Test
    void deveCriarNovoFocoFitossanitarioId() {
        // Act
        FocoFitossanitarioId focoId = FocoFitossanitarioId.novo();

        // Assert
        assertNotNull(focoId);
        assertNotNull(focoId.getValor());
    }
}


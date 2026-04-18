package br.com.cultivafacil.domain.manejo.model;

import br.com.cultivafacil.domain.manejo.event.FocoRegistrado;
import br.com.cultivafacil.domain.manejo.vo.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FocoFitossanitarioTest {

    // Rastreabilidade: F-10 / US-20 / cenário de sucesso
    @Test
    void deveCriarFocoFitossanitarioComSucesso() {
        // Arrange
        ZonaId zonaId = ZonaId.novo();
        CicloAgricolaId cicloAgricolaId = CicloAgricolaId.novo();
        TipoAgronomicoFoco tipo = TipoAgronomicoFoco.PRAGA;
        NivelInfestacao nivel = NivelInfestacao.BAIXO;
        SeveridadeFoco severidade = SeveridadeFoco.BAIXA;
        DescricaoFoco descricao = new DescricaoFoco("Descrição de teste válida.");

        // Act
        FocoFitossanitario foco = new FocoFitossanitario(zonaId, cicloAgricolaId, tipo, nivel, severidade, descricao);

        // Assert
        assertNotNull(foco);
        assertNotNull(foco.getId());
        assertEquals(zonaId, foco.getZonaId());
        assertEquals(cicloAgricolaId, foco.getCicloAgricolaId());
        assertEquals(tipo, foco.getTipo());
        assertEquals(nivel, foco.getNivel());
        assertEquals(severidade, foco.getSeveridade());
        assertEquals(descricao, foco.getDescricao());

        // Assert Event
        List<Object> domainEvents = foco.getDomainEvents();
        assertEquals(1, domainEvents.size());
        assertTrue(domainEvents.get(0) instanceof FocoRegistrado);
        FocoRegistrado evento = (FocoRegistrado) domainEvents.get(0);
        assertEquals(foco.getId(), evento.getFocoFitossanitarioId());
    }

    // Rastreabilidade: F-10 / US-21 / cenário de falha (campos nulos)
    @Test
    void naoDeveCriarFocoFitossanitarioComCamposNulos() {
        assertThrows(NullPointerException.class, () -> new FocoFitossanitario(null, CicloAgricolaId.novo(), TipoAgronomicoFoco.PRAGA, NivelInfestacao.BAIXO, SeveridadeFoco.BAIXA, new DescricaoFoco("Descrição válida.")));
    }
}


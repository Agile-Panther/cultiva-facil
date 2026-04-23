package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IntervaloDeDescansoTest {

    @Test
    @DisplayName("deve criar IntervaloDeDescanso com dados validos")
    void deveCriarIntervaloDeDescansoComDadosValidos() {
        UUID zonaId = UUID.randomUUID();
        NomeCultura nome = new NomeCultura("Tomate");
        DiasDescanso dias = new DiasDescanso(30);

        IntervaloDeDescanso intervalo = new IntervaloDeDescanso(zonaId, nome, dias);

        assertEquals(zonaId, intervalo.getZonaId());
        assertEquals(nome, intervalo.getNomeCultura());
        assertEquals(dias, intervalo.getDiasDescanso());
    }

    @Test
    @DisplayName("deve rejeitar zonaId nulo")
    void deveRejeitarZonaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new IntervaloDeDescanso(null, new NomeCultura("Tomate"), new DiasDescanso(30)));
    }

    @Test
    @DisplayName("deve rejeitar nomeCultura nula")
    void deveRejeitarNomeCulturaNula() {
        assertThrows(NullPointerException.class,
                () -> new IntervaloDeDescanso(UUID.randomUUID(), null, new DiasDescanso(30)));
    }

    @Test
    @DisplayName("deve rejeitar diasDescanso nulo")
    void deveRejeitarDiasDescansoNulo() {
        assertThrows(NullPointerException.class,
                () -> new IntervaloDeDescanso(UUID.randomUUID(), new NomeCultura("Tomate"), null));
    }
}

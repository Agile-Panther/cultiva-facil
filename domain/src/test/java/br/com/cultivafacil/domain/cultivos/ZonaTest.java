package br.com.cultivafacil.domain.cultivos;

import br.com.cultivafacil.domain.cultivos.exception.IntervaloSemHistoricoException;
import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;
import br.com.cultivafacil.domain.cultivos.model.Zona;
import br.com.cultivafacil.domain.cultivos.vo.DiasDescanso;
import br.com.cultivafacil.domain.cultivos.vo.StatusZona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZonaTest {

    @Test
    @DisplayName("RN-01: deve rejeitar vínculo de cultura em Zona com cultivo ativo")
    void deveRejeitarSegundoCultivoAtivo() {
        Zona zona = new Zona();
        zona.vincularCultura("Milho");

        assertThrows(
                ZonaComCultivoAtivoException.class,
                () -> zona.vincularCultura("Feijão")
        );
    }

    @Test
    @DisplayName("deve mudar situacao da Zona para ATIVA ao vincular cultura")
    void deveAtivarZonaAoVincularCultura() {
        Zona zona = new Zona();
        zona.vincularCultura("Milho");

        assertEquals(StatusZona.ATIVA, zona.getSituacao());
    }

    @Test
    @DisplayName("deve registrar ciclo agricola ao vincular cultura")
    void deveRegistrarCicloAoVincularCultura() {
        Zona zona = new Zona();
        zona.vincularCultura("Milho");

        assertEquals(1, zona.getCiclos().size());
        assertEquals("Milho", zona.getCiclos().get(0).getNomeCultura());
    }

    @Test
    @DisplayName("RN-01: deve rejeitar intervalo de descanso sem historico encerrado")
    void deveRejeitarIntervaloSemHistoricoEncerrado() {
        Zona zona = new Zona();
        zona.vincularCultura("Milho");

        assertThrows(
                IntervaloSemHistoricoException.class,
                () -> zona.definirIntervaloDescanso("Milho", new DiasDescanso(30))
        );
    }
}
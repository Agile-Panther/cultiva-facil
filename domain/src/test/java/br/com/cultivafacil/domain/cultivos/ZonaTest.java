package br.com.cultivafacil.domain.cultivos;

import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;
import br.com.cultivafacil.domain.cultivos.model.Zona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ZonaTest {


    @Test
    @DisplayName("RN-01: deve rejeitar vínculo de cultura em Zona com cultivo ativo")
    void deveRejeitarSegundoCultivoAtivo() {
        // Arrange
        Zona zona = new Zona();
        zona.vincularCultura("Milho");


        assertThrows(
                ZonaComCultivoAtivoException.class,
                () -> zona.vincularCultura("Feijão")
        );
    }
}
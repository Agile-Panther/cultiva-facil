package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CicloAgricolaTest {

    private CicloAgricola cicloValido() {
        return new CicloAgricola(
                ZonaId.novo(),
                new NomeCultura("Tomate"),
                new QuantidadePlantada(200.00),
                UnidadeMedidaCiclo.KG
        );
    }

    @Test
    void deveCriarCicloComStatusAtivo() {
        assertEquals(StatusCiclo.ATIVO, cicloValido().getStatus());
    }

    @Test
    void deveGerarIdAoCriar() {
        assertNotNull(cicloValido().getId());
    }

    @Test
    void deveRejeitarAlteracaoDaQuantidadePlantada() {
        var ciclo = cicloValido();
        assertThrows(IllegalStateException.class,
                () -> ciclo.alterarQuantidadePlantada(new QuantidadePlantada(500.00)));
    }

    @Test
    void deveRejeitarAlteracaoDaUnidadeAposInicio() {
        var ciclo = cicloValido();
        assertThrows(IllegalStateException.class,
                () -> ciclo.alterarUnidade(UnidadeMedidaCiclo.GRAMAS));
    }

    @Test
    void deveEncerrarCicloAtivo() {
        var ciclo = cicloValido();
        ciclo.encerrar();
        assertEquals(StatusCiclo.ENCERRADO, ciclo.getStatus());
    }

    @Test
    void deveRejeitarEncerrarCicloJaEncerrado() {
        var ciclo = cicloValido();
        ciclo.encerrar();
        assertThrows(IllegalStateException.class, ciclo::encerrar);
    }
}

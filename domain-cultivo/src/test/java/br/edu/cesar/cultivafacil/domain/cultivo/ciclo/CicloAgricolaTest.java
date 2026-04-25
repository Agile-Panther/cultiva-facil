package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CicloAgricolaTest {

    private CicloAgricola cicloValido() {
        return new CicloAgricola(
                TalhaoId.novo(),
                new NomeCultura("Tomate"),
                new QuantidadePlantada(200.00),
                UnidadeMedidaCiclo.QUILOGRAMA
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

    // F-06 RN-046
    @Test
    void deveRejeitarAlteracaoDaQuantidadePlantada() {
        var ciclo = cicloValido();
        assertThrows(IllegalStateException.class,
                () -> ciclo.alterarQuantidadePlantada(new QuantidadePlantada(500.00)));
    }

    // F-06 RN-045
    @Test
    void deveRejeitarAlteracaoDaUnidadeAposInicio() {
        var ciclo = cicloValido();
        assertThrows(IllegalStateException.class,
                () -> ciclo.alterarUnidade(UnidadeMedidaCiclo.GRAMA));
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

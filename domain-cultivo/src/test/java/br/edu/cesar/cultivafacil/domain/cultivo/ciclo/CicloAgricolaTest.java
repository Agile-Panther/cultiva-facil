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
    void deveEncerrarCicloAtivo() {
        var ciclo = cicloValido();
        ciclo.encerrar();
        assertEquals(StatusCiclo.ENCERRADO, ciclo.getStatus());
    }

    @Test
    void deveRejeitarEncerrarCicloJaEncerrado() {
        var ciclo = cicloValido();
        ciclo.encerrar();
        var ex = assertThrows(IllegalStateException.class, ciclo::encerrar);
        assertTrue(ex.getMessage().contains("CICLO_INVALIDO"));
    }

    // F-06 RN-055
    @Test
    void deveCancelarCicloAtivoComJustificativa() {
        var ciclo = cicloValido();
        var justificativa = new JustificativaCancelamento("Esta justificativa tem mais de vinte caracteres");
        ciclo.cancelar(justificativa);
        assertEquals(StatusCiclo.CANCELADO, ciclo.getStatus());
        assertNotNull(ciclo.getCancelamento());
    }

    // F-06 RN-056
    @Test
    void deveRejeitarCancelarCicloEncerrado() {
        var ciclo = cicloValido();
        ciclo.encerrar();
        var justificativa = new JustificativaCancelamento("Esta justificativa tem mais de vinte caracteres");
        var ex = assertThrows(IllegalStateException.class, () -> ciclo.cancelar(justificativa));
        assertTrue(ex.getMessage().contains("CICLO_INVALIDO"));
    }

    @Test
    void deveRejeitarCancelarCicloJaCancelado() {
        var ciclo = cicloValido();
        var justificativa = new JustificativaCancelamento("Esta justificativa tem mais de vinte caracteres");
        ciclo.cancelar(justificativa);
        var ex = assertThrows(IllegalStateException.class, () -> ciclo.cancelar(justificativa));
        assertTrue(ex.getMessage().contains("CICLO_INVALIDO"));
    }

    @Test
    void deveRejeitarEncerrarCicloCancelado() {
        var ciclo = cicloValido();
        var justificativa = new JustificativaCancelamento("Esta justificativa tem mais de vinte caracteres");
        ciclo.cancelar(justificativa);
        var ex = assertThrows(IllegalStateException.class, ciclo::encerrar);
        assertTrue(ex.getMessage().contains("CICLO_INVALIDO"));
    }
}

package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CulturaTest {

    private final PropriedadeId propriedadeId = PropriedadeId.novo();

    @Test
    void deveCriarCulturaCustomizadaAtivaComFamiliaBotanica() {
        Cultura cultura = Cultura.customizada(propriedadeId, "Milho", "AG-30", "Poaceae");

        assertNotNull(cultura.getId());
        assertEquals("Milho", cultura.getNomeComum().getValor());
        assertEquals("AG-30", cultura.getVariedade().getValor());
        assertEquals("Poaceae", cultura.getFamiliaBotanica().orElseThrow().getValor());
        assertEquals(OrigemCultura.CUSTOMIZADA, cultura.getOrigem());
        assertTrue(cultura.ativa());
    }

    @Test
    void deveRejeitarNomeComumMenorQueDoisCaracteres() {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> new NomeComumCultura("A"));

        assertTrue(excecao.getMessage().contains("NOME_INVALIDO"));
    }

    @Test
    void deveRejeitarVariedadeVazia() {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> new Variedade(" "));

        assertTrue(excecao.getMessage().contains("NOME_INVALIDO"));
    }

    @Test
    void deveRejeitarFamiliaBotanicaForaDoLimite() {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> new FamiliaBotanica("A"));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }

    @Test
    void devePermitirReconstituirCulturaCustomizadaSemFamiliaParaValidacaoDeCompatibilidade() {
        Cultura cultura = Cultura.customizadaSemFamilia(propriedadeId, "Milho-Custom", "Local");

        assertTrue(cultura.customizadaSemFamiliaBotanica());
        assertTrue(cultura.getFamiliaBotanica().isEmpty());
    }

    @Test
    void deveInativarEAtivarCultura() {
        Cultura cultura = Cultura.nativa(propriedadeId, "Feijao", "Comum", "Fabaceae");

        cultura.inativar();
        assertFalse(cultura.ativa());

        cultura.ativar();
        assertTrue(cultura.ativa());
    }
}

package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelacaoCompatibilidadeTest {

    @Test
    void deveCriarRelacaoEntreDuasCulturasDistintas() {
        CulturaId milho = CulturaId.novo();
        CulturaId feijao = CulturaId.novo();

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(
            milho, feijao, ClassificacaoConsorcio.COMPANHEIRA, "Fixação de nitrogenio");

        assertNotNull(relacao.getId());
        assertTrue(relacao.envolve(milho, feijao));
        assertTrue(relacao.envolve(feijao, milho));
        assertEquals(ClassificacaoConsorcio.COMPANHEIRA, relacao.getClassificacao());
    }

    @Test
    void deveRejeitarRelacaoDaMesmaCultura() {
        CulturaId milho = CulturaId.novo();

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> new RelacaoCompatibilidade(milho, milho, ClassificacaoConsorcio.NEUTRA, ""));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }
}

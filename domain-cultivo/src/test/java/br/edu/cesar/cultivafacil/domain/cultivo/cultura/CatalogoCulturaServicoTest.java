package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogoCulturaServicoTest {

    private CulturaRepositorio culturaRepositorio;
    private CatalogoCulturaServico servico;
    private PropriedadeId propriedadeId;

    @BeforeEach
    void setUp() {
        culturaRepositorio = mock(CulturaRepositorio.class);
        servico = new CatalogoCulturaServico(culturaRepositorio);
        propriedadeId = PropriedadeId.novo();
    }

    @Test
    void deveCadastrarCulturaCustomizadaQuandoNomeVariedadeEFamiliaSaoValidos() {
        when(culturaRepositorio.existePorNomeEVariedade(eq(propriedadeId), any(), any())).thenReturn(false);

        Cultura cultura = servico.cadastrarCustomizada(propriedadeId, "Milho", "AG-30", "Poaceae");

        assertEquals("Milho", cultura.getNomeComum().getValor());
        assertEquals("AG-30", cultura.getVariedade().getValor());
        assertEquals("Poaceae", cultura.getFamiliaBotanica().orElseThrow().getValor());
        verify(culturaRepositorio).salvar(cultura);
    }

    @Test
    void deveRejeitarCadastroDuplicadoPorNomeEVariedade() {
        when(culturaRepositorio.existePorNomeEVariedade(eq(propriedadeId), any(), any())).thenReturn(true);

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> servico.cadastrarCustomizada(propriedadeId, "Milho", "AG-30", "Poaceae"));

        assertTrue(excecao.getMessage().contains("combinação de nome e variedade já existe no catálogo"));
        verify(culturaRepositorio, never()).salvar(any());
    }
}

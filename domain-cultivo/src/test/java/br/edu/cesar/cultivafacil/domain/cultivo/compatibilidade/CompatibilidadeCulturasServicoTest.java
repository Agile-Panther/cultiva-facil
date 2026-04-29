package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.cultura.Cultura;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.NomeComumCultura;
import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompatibilidadeCulturasServicoTest {

    private CulturaRepositorio culturaRepositorio;
    private RelacaoCompatibilidadeRepositorio relacaoRepositorio;
    private CompatibilidadeCulturasServico servico;
    private PropriedadeId propriedadeId;
    private Cultura milho;
    private Cultura feijao;

    @BeforeEach
    void setUp() {
        culturaRepositorio = mock(CulturaRepositorio.class);
        relacaoRepositorio = mock(RelacaoCompatibilidadeRepositorio.class);
        servico = new CompatibilidadeCulturasServico(culturaRepositorio, relacaoRepositorio);
        propriedadeId = PropriedadeId.novo();
        milho = Cultura.nativa(propriedadeId, "Milho", "AG-30", "Poaceae");
        feijao = Cultura.nativa(propriedadeId, "Feijao", "Comum", "Fabaceae");
    }

    @Test
    void deveAceitarCompatibilidadeEntreDuasCulturasAtivasCadastradas() {
        prepararBusca(milho, feijao);
        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(
            milho.getId(), feijao.getId(), ClassificacaoConsorcio.COMPANHEIRA, "Fixação de nitrogenio");
        when(relacaoRepositorio.buscarPorCulturas(milho.getId(), feijao.getId())).thenReturn(Optional.of(relacao));

        ResultadoCompatibilidade resultado = servico.verificar(propriedadeId, List.of("Milho", "Feijao"));

        assertEquals(ClassificacaoConsorcio.COMPANHEIRA, resultado.getClassificacao());
        assertEquals("Fixação de nitrogenio", resultado.getBeneficioAgronomico());
    }

    @Test
    void deveRejeitarCulturaAusenteDoCatalogo() {
        when(culturaRepositorio.buscarPorNome(eq(propriedadeId), any(NomeComumCultura.class)))
            .thenReturn(Optional.of(milho), Optional.empty());

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> servico.verificar(propriedadeId, List.of("Milho", "Feijao")));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }

    @Test
    void deveRejeitarCulturaInativa() {
        feijao.inativar();
        prepararBusca(milho, feijao);

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> servico.verificar(propriedadeId, List.of("Milho", "Feijao")));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }

    @Test
    void deveRejeitarQuantidadeDiferenteDeDuasCulturas() {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> servico.verificar(propriedadeId, List.of("Milho")));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }

    @Test
    void deveRejeitarMesmaCulturaRepetida() {
        when(culturaRepositorio.buscarPorNome(eq(propriedadeId), any(NomeComumCultura.class)))
            .thenReturn(Optional.of(milho), Optional.of(milho));

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> servico.verificar(propriedadeId, List.of("Milho", "Milho")));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }

    @Test
    void deveRejeitarCustomizadaSemFamiliaBotanica() {
        Cultura customizada = Cultura.customizadaSemFamilia(propriedadeId, "Milho-Custom", "Local");
        when(culturaRepositorio.buscarPorNome(eq(propriedadeId), any(NomeComumCultura.class)))
            .thenReturn(Optional.of(customizada), Optional.of(feijao));

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
            () -> servico.verificar(propriedadeId, List.of("Milho-Custom", "Feijao")));

        assertTrue(excecao.getMessage().contains("CULTURA_INVALIDO"));
    }

    private void prepararBusca(Cultura primeira, Cultura segunda) {
        when(culturaRepositorio.buscarPorNome(eq(propriedadeId), any(NomeComumCultura.class)))
            .thenReturn(Optional.of(primeira), Optional.of(segunda));
    }
}

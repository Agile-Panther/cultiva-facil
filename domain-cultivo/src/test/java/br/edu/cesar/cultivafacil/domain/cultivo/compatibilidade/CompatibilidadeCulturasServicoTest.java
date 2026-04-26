package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompatibilidadeCulturasServicoTest {

    @Mock
    private RelacaoCompatibilidadeRepositorio relacaoRepositorio;

    private CompatibilidadeCulturasServico servico;

    private TalhaoId talhaoId;
    private NomeCultura culturaAtiva;
    private NomeCultura culturaNova;

    @BeforeEach
    void setUp() {
        servico = new CompatibilidadeCulturasServico(relacaoRepositorio);
        talhaoId = TalhaoId.novo();
        culturaAtiva = new NomeCultura("Tomate");
        culturaNova = new NomeCultura("Manjericão");
    }

    // US-13 — RN-049: relação Companheira → status COMPANHEIRA
    @Test
    void deveRegistrarConsorcioCulturaCompanheiraComStatusCompanheira() {
        RelacaoCompatibilidade relacaoCompanheira = new RelacaoCompatibilidade(
            culturaAtiva, culturaNova, ClassificacaoConsorcio.COMPANHEIRA);
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, culturaNova))
            .thenReturn(Optional.of(relacaoCompanheira));

        ConsorcioCultura resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, false);

        assertEquals(ClassificacaoConsorcio.COMPANHEIRA, resultado.getClassificacao());
        assertFalse(resultado.isCienciaDoAgricultor());
        verify(relacaoRepositorio).salvarConsorcio(resultado);
    }

    // US-13 — RN-049: sem relação definida → status NEUTRA, sem Companheira
    @Test
    void deveRegistrarConsorcioNeutroQuandoNaoHaRelacaoDefinida() {
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, culturaNova))
            .thenReturn(Optional.empty());

        ConsorcioCultura resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, false);

        assertEquals(ClassificacaoConsorcio.NEUTRA, resultado.getClassificacao());
        assertFalse(resultado.isCienciaDoAgricultor());
        verify(relacaoRepositorio).salvarConsorcio(resultado);
    }

    // US-13 — RN-047: cultura Inimiga sem consentimento → INIMIGA_BLOQUEADA
    @Test
    void deveRejeitarInimigaSemConsentimento() {
        NomeCultura funcho = new NomeCultura("Funcho");
        RelacaoCompatibilidade relacaoInimiga = new RelacaoCompatibilidade(
            culturaAtiva, funcho, ClassificacaoConsorcio.INIMIGA);
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, funcho))
            .thenReturn(Optional.of(relacaoInimiga));

        IllegalStateException excecao = assertThrows(IllegalStateException.class,
            () -> servico.registrarConsorcio(talhaoId, culturaAtiva, funcho, false));

        assertTrue(excecao.getMessage().contains("INIMIGA_BLOQUEADA"));
        verify(relacaoRepositorio, never()).salvarConsorcio(any());
    }

    // US-13 — RN-048: cultura Inimiga com consentimento → cienciaDoAgricultor = true
    @Test
    void deveRegistrarInimigaComConsentimentoECienciaAtiva() {
        NomeCultura funcho = new NomeCultura("Funcho");
        RelacaoCompatibilidade relacaoInimiga = new RelacaoCompatibilidade(
            culturaAtiva, funcho, ClassificacaoConsorcio.INIMIGA);
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, funcho))
            .thenReturn(Optional.of(relacaoInimiga));

        ConsorcioCultura resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, funcho, true);

        assertEquals(ClassificacaoConsorcio.INIMIGA, resultado.getClassificacao());
        assertTrue(resultado.isCienciaDoAgricultor());
        verify(relacaoRepositorio).salvarConsorcio(resultado);
    }

    // US-14: histórico retornado quando há consórcios registrados
    @Test
    void deveRetornarHistoricoQuandoTalhaoTemConsorcios() {
        NomeCultura cultura = new NomeCultura("Cenoura");
        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.NEUTRA, false);
        when(relacaoRepositorio.listarConsorcioPorTalhao(talhaoId)).thenReturn(List.of(consorcio));

        List<ConsorcioCultura> historico = servico.consultarHistorico(talhaoId);

        assertEquals(1, historico.size());
        assertEquals(consorcio, historico.get(0));
        verify(relacaoRepositorio).listarConsorcioPorTalhao(talhaoId);
    }

    // US-14 — RN-050: sem consórcios → HISTORICO_INEXISTENTE
    @Test
    void deveRejeitarHistoricoQuandoTalhaoSemNenhumConsorcio() {
        when(relacaoRepositorio.listarConsorcioPorTalhao(talhaoId)).thenReturn(List.of());

        IllegalStateException excecao = assertThrows(IllegalStateException.class,
            () -> servico.consultarHistorico(talhaoId));

        assertTrue(excecao.getMessage().contains("HISTORICO_INEXISTENTE"));
    }

    @Test
    void deveRejeitarRegistroComTalhaoIdNulo() {
        assertThrows(NullPointerException.class,
            () -> servico.registrarConsorcio(null, culturaAtiva, culturaNova, false));
    }

    @Test
    void deveRejeitarRegistroComCulturaAtivaNula() {
        assertThrows(NullPointerException.class,
            () -> servico.registrarConsorcio(talhaoId, null, culturaNova, false));
    }

    @Test
    void deveRejeitarRegistroComCulturaNovaNula() {
        assertThrows(NullPointerException.class,
            () -> servico.registrarConsorcio(talhaoId, culturaAtiva, null, false));
    }

    @Test
    void deveRejeitarConsultaHistoricoComTalhaoIdNulo() {
        assertThrows(NullPointerException.class,
            () -> servico.consultarHistorico(null));
    }

    @Test
    void deveChamarRepositorioUmaVezAoBuscarRelacao() {
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, culturaNova))
            .thenReturn(Optional.empty());

        servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, false);

        verify(relacaoRepositorio, times(1)).buscarPorCulturas(culturaAtiva, culturaNova);
    }
}

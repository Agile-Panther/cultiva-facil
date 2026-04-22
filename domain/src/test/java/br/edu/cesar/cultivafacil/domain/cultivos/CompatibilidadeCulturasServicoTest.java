package br.edu.cesar.cultivafacil.domain.cultivos;

import br.edu.cesar.cultivafacil.domain.cultivos.repository.RelacaoCompatibilidadeRepositorio;
import br.edu.cesar.cultivafacil.shared.ZonaId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompatibilidadeCulturasServicoTest {

    @Mock
    private RelacaoCompatibilidadeRepositorio relacaoRepositorio;

    private CompatibilidadeCulturasServico servico;

    private ZonaId zonaId;
    private NomeCultura culturaAtiva;
    private NomeCultura culturaNova;

    @BeforeEach
    void setUp() {
        servico = new CompatibilidadeCulturasServico(relacaoRepositorio);
        zonaId = ZonaId.novo();
        culturaAtiva = new NomeCultura("Tomate");
        culturaNova = new NomeCultura("Manjericão");
    }

    @Test
    void deveRegistrarConsorcioCulturaCompanheiraComStatusCompanheira() {
        RelacaoCompatibilidade relacaoCompanheira = new RelacaoCompatibilidade(
                culturaAtiva, culturaNova, ClassificacaoConsorcio.COMPANHEIRA);
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, culturaNova))
                .thenReturn(Optional.of(relacaoCompanheira));

        ConsorcioCultura resultado = servico.registrarConsorcio(zonaId, culturaAtiva, culturaNova, false);

        assertThat(resultado.getClassificacao()).isEqualTo(ClassificacaoConsorcio.COMPANHEIRA);
        assertThat(resultado.isCienciaDoAgricultor()).isFalse();
        verify(relacaoRepositorio).salvarConsorcio(resultado);
    }

    @Test
    void deveRegistrarConsorcioNeutroSemStatusCompanheira() {
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, culturaNova))
                .thenReturn(Optional.empty());

        ConsorcioCultura resultado = servico.registrarConsorcio(zonaId, culturaAtiva, culturaNova, false);

        assertThat(resultado.getClassificacao()).isEqualTo(ClassificacaoConsorcio.NEUTRA);
        assertThat(resultado.isCienciaDoAgricultor()).isFalse();
        verify(relacaoRepositorio).salvarConsorcio(resultado);
    }

    @Test
    void deveRejeitarInimigaSemConsentimento() {
        NomeCultura funcho = new NomeCultura("Funcho");
        RelacaoCompatibilidade relacaoInimiga = new RelacaoCompatibilidade(
                culturaAtiva, funcho, ClassificacaoConsorcio.INIMIGA);
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, funcho))
                .thenReturn(Optional.of(relacaoInimiga));

        assertThatThrownBy(() -> servico.registrarConsorcio(zonaId, culturaAtiva, funcho, false))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("INIMIGA_BLOQUEADA");
    }

    @Test
    void deveRegistrarInimigaComConsentimentoECienciaAtiva() {
        NomeCultura funcho = new NomeCultura("Funcho");
        RelacaoCompatibilidade relacaoInimiga = new RelacaoCompatibilidade(
                culturaAtiva, funcho, ClassificacaoConsorcio.INIMIGA);
        when(relacaoRepositorio.buscarPorCulturas(culturaAtiva, funcho))
                .thenReturn(Optional.of(relacaoInimiga));

        ConsorcioCultura resultado = servico.registrarConsorcio(zonaId, culturaAtiva, funcho, true);

        assertThat(resultado.getClassificacao()).isEqualTo(ClassificacaoConsorcio.INIMIGA);
        assertThat(resultado.isCienciaDoAgricultor()).isTrue();
        verify(relacaoRepositorio).salvarConsorcio(resultado);
    }

    @Test
    void deveRetornarHistoricoQuandoZonaPossuiConsorcioRegistrado() {
        NomeCultura cultura = new NomeCultura("Cenoura");
        ConsorcioCultura consorcio = new ConsorcioCultura(zonaId, cultura, ClassificacaoConsorcio.NEUTRA, false);
        when(relacaoRepositorio.listarConsorcioPorZona(zonaId)).thenReturn(List.of(consorcio));

        List<ConsorcioCultura> historico = servico.consultarHistorico(zonaId);

        assertThat(historico).hasSize(1);
        assertThat(historico.get(0)).isEqualTo(consorcio);
    }

    @Test
    void deveRejeitarHistoricoQuandoZonaSemNenhumConsorcio() {
        when(relacaoRepositorio.listarConsorcioPorZona(zonaId)).thenReturn(List.of());

        assertThatThrownBy(() -> servico.consultarHistorico(zonaId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HISTORICO_INEXISTENTE");
    }
}

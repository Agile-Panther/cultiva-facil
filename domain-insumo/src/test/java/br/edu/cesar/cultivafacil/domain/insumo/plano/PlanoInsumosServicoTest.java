package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanoInsumosServicoTest {

    @Mock
    private PlanoInsumosRepositorio repositorio;

    @InjectMocks
    private PlanoInsumosServico servico;

    private TalhaoId talhaoId;
    private ContaId contaId;
    private MesReferencia mesFuturo;

    @BeforeEach
    void setUp() {
        talhaoId = TalhaoId.novo();
        contaId = ContaId.novo();
        mesFuturo = new MesReferencia(YearMonth.now().plusMonths(1));
    }

    // US-28 RN-103
    @Test
    void deveRejeitarPlanoDuplicadoParaMesmaZonaEMes() {
        when(repositorio.existePorTalhaoEMes(talhaoId, mesFuturo)).thenReturn(true);

        assertThatThrownBy(() -> servico.criarPlano(talhaoId, contaId, mesFuturo))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Ja existe plano de insumos para esta Zona neste mes");

        verify(repositorio).existePorTalhaoEMes(talhaoId, mesFuturo);
        verify(repositorio, never()).salvar(any());
    }

    // US-28 positivo
    @Test
    void deveCriarPlanoEPersistirQuandoNaoHaDuplicata() {
        when(repositorio.existePorTalhaoEMes(talhaoId, mesFuturo)).thenReturn(false);

        PlanoInsumos plano = servico.criarPlano(talhaoId, contaId, mesFuturo);

        assertThat(plano).isNotNull();
        assertThat(plano.getTalhaoId()).isEqualTo(talhaoId);
        assertThat(plano.getContaId()).isEqualTo(contaId);
        assertThat(plano.getMesReferencia()).isEqualTo(mesFuturo);
        verify(repositorio).salvar(plano);
    }

    // US-28 positivo: verifica que salvar é chamado exatamente uma vez
    @Test
    void deveChamarSalvarExatamenteUmaVezAoCriarPlano() {
        when(repositorio.existePorTalhaoEMes(talhaoId, mesFuturo)).thenReturn(false);

        servico.criarPlano(talhaoId, contaId, mesFuturo);

        verify(repositorio, times(1)).salvar(any(PlanoInsumos.class));
    }

    // US-30 RN-108
    @Test
    void deveRetornarHistoricoVazioQuandoNaoHaItensAdquiridos() {
        PlanoInsumos planoSemAquisicoes = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        planoSemAquisicoes.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        when(repositorio.listarHistoricoPorTalhao(talhaoId)).thenReturn(List.of(planoSemAquisicoes));

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).isEmpty();
        verify(repositorio).listarHistoricoPorTalhao(talhaoId);
    }

    // US-30 positivo
    @Test
    void deveRetornarHistoricoComItensAdquiridosDeplanosEncerrados() {
        PlanoInsumos planoEncerrado = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = planoEncerrado.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        planoEncerrado.encerrar();
        when(repositorio.listarHistoricoPorTalhao(talhaoId)).thenReturn(List.of(planoEncerrado));

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).hasSize(1);
        assertThat(historico.get(0).getStatus()).isEqualTo(StatusItemInsumo.ADQUIRIDO);
        verify(repositorio).listarHistoricoPorTalhao(talhaoId);
    }

    // US-30: repositório retorna lista vazia
    @Test
    void deveRetornarHistoricoVazioQuandoRepositorioRetornarListaVazia() {
        when(repositorio.listarHistoricoPorTalhao(talhaoId)).thenReturn(List.of());

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).isEmpty();
        verify(repositorio).listarHistoricoPorTalhao(talhaoId);
    }

    // US-30: itens Planejados são excluídos mesmo em planos encerrados
    @Test
    void deveExcluirItensPlanejaosDoHistoricoMesmoEmPlanosEncerrados() {
        PlanoInsumos planoMisto = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo adquirido = planoMisto.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        adquirido.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        planoMisto.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
        planoMisto.encerrar();
        when(repositorio.listarHistoricoPorTalhao(talhaoId)).thenReturn(List.of(planoMisto));

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).hasSize(1);
        assertThat(historico.get(0).getStatus()).isEqualTo(StatusItemInsumo.ADQUIRIDO);
    }

    // US-30: múltiplos planos encerrados acumulam itens no histórico
    @Test
    void deveAcumularItensDeMultiplosPlanosEncerradosNoHistorico() {
        MesReferencia outroMes = new MesReferencia(YearMonth.now().plusMonths(2));

        PlanoInsumos plano1 = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item1 = plano1.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        item1.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano1.encerrar();

        PlanoInsumos plano2 = new PlanoInsumos(talhaoId, contaId, outroMes);
        ItemInsumo item2 = plano2.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(8), UnidadeMedidaInsumo.KG);
        item2.registrarAquisicao(new PrecoUnitario(new BigDecimal("20.00")));
        plano2.encerrar();

        when(repositorio.listarHistoricoPorTalhao(talhaoId)).thenReturn(List.of(plano1, plano2));

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).hasSize(2);
        assertThat(historico).allMatch(i -> i.getStatus() == StatusItemInsumo.ADQUIRIDO);
    }
}
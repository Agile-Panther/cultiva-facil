package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlanoInsumosTest {

    private TalhaoId talhaoId;
    private ContaId contaId;
    private MesReferencia mesFuturo;

    @BeforeEach
    void setUp() {
        talhaoId = TalhaoId.novo();
        contaId = ContaId.novo();
        mesFuturo = new MesReferencia(YearMonth.now().plusMonths(1));
    }

    // US-28 RN-102
    @Test
    void deveRejeitarCriacaoComMesDeReferenciaPassado() {
        YearMonth mesPassado = YearMonth.now().minusMonths(1);

        assertThrows(InsumoDomainException.class, () -> new MesReferencia(mesPassado));
    }

    // US-28 RN-105
    @Test
    void deveRejeitarQuantidadeDeInsumoZeroOuNegativa() {
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(0));
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(-1));
    }

    // US-28 positivo
    @Test
    void deveCriarPlanoComItemAdicionado() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);

        assertThat(plano.getId()).isNotNull();
        assertThat(plano.getItens()).hasSize(1);
        assertThat(item.getTipo()).isEqualTo(TipoInsumo.SEMENTE);
        assertThat(item.getStatus()).isEqualTo(StatusItemInsumo.PLANEJADO);
    }

    // US-28 positivo: evento PlanoCriado gerado
    @Test
    void deveGerarEventoPlanoCriadoAoCriarPlano() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);

        assertThat(plano.getEventos()).hasSize(1);
        assertThat(plano.getEventos().get(0)).isInstanceOf(PlanoInsumos.PlanoCriado.class);
    }

    // US-29 RN-106
    @Test
    void deveRejeitarTransicaoParaAdquiridoSemPrecoUnitario() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);

        assertThrows(InsumoDomainException.class, () -> item.registrarAquisicao(null));
    }

    // US-29 RN-107
    @Test
    void deveRejeitarReversaoDeStatusAdquiridoParaPlanejado() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("50.00")));

        assertThrows(InsumoDomainException.class, item::reverterParaPlanejado);
    }

    // US-29 RN-107 mensagem correta
    @Test
    void deveRejeitarReversaoComMensagemCorreta() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("50.00")));

        assertThatThrownBy(item::reverterParaPlanejado)
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Item adquirido nao pode retornar ao status Planejado");
    }

    // US-29 positivo
    @Test
    void deveRegistrarAquisicaoComPrecoUnitario() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
        PrecoUnitario preco = new PrecoUnitario(new BigDecimal("25.00"));

        plano.registrarAquisicao(item.getId(), preco);

        assertThat(item.getStatus()).isEqualTo(StatusItemInsumo.ADQUIRIDO);
        assertThat(item.getPrecoUnitario()).isEqualTo(preco);
        assertThat(item.getDataAquisicao()).isNotNull();
    }

    // US-29 positivo: evento ItemAdquirido gerado
    @Test
    void deveGerarEventoItemAdquiridoAoRegistrarAquisicao() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
        plano.limparEventos();

        plano.registrarAquisicao(item.getId(), new PrecoUnitario(new BigDecimal("10.00")));

        assertThat(plano.getEventos()).hasSize(1);
        assertThat(plano.getEventos().get(0)).isInstanceOf(PlanoInsumos.ItemAdquirido.class);
    }

    // US-46 RN-109
    @Test
    void deveRejeitarConsumoSemAquisicaoPrevia() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);

        assertThatThrownBy(() ->
                plano.registrarConsumo(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Nenhuma aquisicao registrada para este insumo nesta Zona");
    }

    // US-46 RN-110
    @Test
    void deveRejeitarConsumoAcimaDoSaldoDisponivel() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("15.00")));

        assertThatThrownBy(() ->
                plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(50), UnidadeMedidaInsumo.KG))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Quantidade consumida excede o saldo disponivel");
    }

    // US-46 RN-111
    @Test
    void deveRejeitarConsumoComUnidadeDivergente() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("15.00")));

        assertThatThrownBy(() ->
                plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.LITRO))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Unidade de medida do consumo difere da unidade de aquisicao");
    }

    // US-46 positivo
    @Test
    void deveRegistrarConsumoQuandoSaldoSuficiente() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("15.00")));

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);

        assertThat(plano.getConsumos()).hasSize(1);
        assertThat(plano.getConsumos().get(0).getQuantidade().getValor()).isEqualTo(10);
    }

    // US-47 RN-112
    @Test
    void deveRejeitarLimiteMinimoZeroOuNegativo() {
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(0));
        assertThrows(InsumoDomainException.class, () -> new QuantidadeInsumo(-1));
    }

    // US-47 RN-113
    @Test
    void deveRejeitarConfiguracaoSemEmailDestinatario() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));

        assertThatThrownBy(() ->
                plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, null))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Email do destinatario obrigatorio para configurar limite");

        assertThatThrownBy(() ->
                plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, ""))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Email do destinatario obrigatorio para configurar limite");
    }

    // US-47 RN-114
    @Test
    void deveRejeitarConfiguracaoDuplicadaParaMesmoInsumoEZona() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");

        assertThatThrownBy(() ->
                plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(3), UnidadeMedidaInsumo.KG, "outro@fazenda.com"))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Ja existe configuracao de limite ativa para este insumo nesta Zona");
    }

    // US-47 RN-115
    @Test
    void deveRejeitarLimiteMinimoParaInsumoSemAquisicao() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        plano.adicionarItem(TipoInsumo.DEFENSIVO, new QuantidadeInsumo(10), UnidadeMedidaInsumo.LITRO);

        assertThatThrownBy(() ->
                plano.configurarLimiteMinimo(TipoInsumo.DEFENSIVO, new QuantidadeInsumo(2), UnidadeMedidaInsumo.LITRO, "gestor@fazenda.com"))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Nenhuma aquisicao registrada para este insumo nesta Zona");
    }

    // US-47 positivo
    @Test
    void deveCriarConfiguracaoLimiteComoAtiva() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));

        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");

        assertThat(plano.getConfiguracoes()).hasSize(1);
        assertThat(plano.getConfiguracoes().get(0).isAtiva()).isTrue();
        assertThat(plano.getConfiguracoes().get(0).getEmailDestinatario()).isEqualTo("gestor@fazenda.com");
    }

    // US-48 RN-116
    @Test
    void deveGerarPedidoReposicaoQuandoSaldoAtingeLimiteMinimo() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(6), UnidadeMedidaInsumo.KG);

        assertThat(plano.getPedidosReposicao()).hasSize(1);
        assertThat(plano.getPedidosReposicao().get(0).getTipo()).isEqualTo(TipoInsumo.FERTILIZANTE);
        assertThat(plano.getPedidosReposicao().get(0).getEmailDestinatario()).isEqualTo("gestor@fazenda.com");
    }

    // US-48 RN-117
    @Test
    void naoDeveGerarNovoPedidoReposicaoDentroDeVinteQuatroHoras() {
        ConfiguracaoLimite config = new ConfiguracaoLimite(
                UUID.randomUUID(), TipoInsumo.FERTILIZANTE,
                new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG,
                "gestor@fazenda.com", true, LocalDateTime.now());
        ItemInsumo item = new ItemInsumo(
                UUID.randomUUID(), TipoInsumo.FERTILIZANTE,
                new QuantidadeInsumo(15), UnidadeMedidaInsumo.KG,
                StatusItemInsumo.ADQUIRIDO, new PrecoUnitario(new BigDecimal("10.00")),
                LocalDateTime.now().minusDays(1));
        PlanoInsumos plano = new PlanoInsumos(
                PlanoInsumosId.novo(), talhaoId, contaId, mesFuturo,
                List.of(item), List.of(), List.of(config), List.of(), false);

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(11), UnidadeMedidaInsumo.KG);

        assertThat(plano.getPedidosReposicao()).isEmpty();
    }

    // US-48 RN-118: pedido contém informações completas
    @Test
    void devePedidoReposicaoConterInformacoesCompletas() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(16), UnidadeMedidaInsumo.KG);

        PedidoReposicao pedido = plano.getPedidosReposicao().get(0);
        assertThat(pedido.getTipo()).isEqualTo(TipoInsumo.FERTILIZANTE);
        assertThat(pedido.getTalhaoId()).isEqualTo(talhaoId);
        assertThat(pedido.getSaldoNoMomento()).isEqualTo(4.0);
        assertThat(pedido.getQuantidadeSugerida()).isGreaterThanOrEqualTo(0);
        assertThat(pedido.getEmailDestinatario()).isEqualTo("gestor@fazenda.com");
        assertThat(pedido.getDataHoraDisparo()).isNotNull();
    }

    // US-48 RN-120
    @Test
    void naoDeveGerarPedidoReposicaoSemConfiguracaoDeLimite() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("5.00")));

        plano.registrarConsumo(TipoInsumo.SEMENTE, new QuantidadeInsumo(15), UnidadeMedidaInsumo.KG);

        assertThat(plano.getPedidosReposicao()).isEmpty();
    }

    // US-48 RN-116: saldo acima do limite não gera pedido
    @Test
    void naoDeveGerarPedidoQuandoSaldoPermanecerAcimaDoLimite() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);

        assertThat(plano.getPedidosReposicao()).isEmpty();
    }

    // US-48 RN-119: PedidoReposicao transita de PENDENTE para ENVIADO
    @Test
    void devePedidoReposicaoTransitarParaEnviadoAposMarcarComoEnviado() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(16), UnidadeMedidaInsumo.KG);

        PedidoReposicao pedido = plano.getPedidosReposicao().get(0);
        assertThat(pedido.getStatusEnvio()).isEqualTo(StatusEnvioPedido.PENDENTE);

        pedido.marcarComoEnviado();

        assertThat(pedido.getStatusEnvio()).isEqualTo(StatusEnvioPedido.ENVIADO);
    }

    // US-48: evento ReposicaoSolicitada gerado ao emitir pedido
    @Test
    void deveGerarEventoReposicaoSolicitadaAoEmitirPedido() {
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
        plano.limparEventos();

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(16), UnidadeMedidaInsumo.KG);

        assertThat(plano.getEventos()).hasSize(1);
        assertThat(plano.getEventos().get(0)).isInstanceOf(PlanoInsumos.ReposicaoSolicitada.class);
    }
}
package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlanoInsumosIntegrationTest {

    private TalhaoId talhaoId;
    private ContaId contaId;
    private MesReferencia mesFuturo;
    private PlanoInsumosRepositorioEmMemoria repositorio;
    private PlanoInsumosServico servico;

    @BeforeEach
    void setUp() {
        talhaoId = TalhaoId.novo();
        contaId = ContaId.novo();
        mesFuturo = new MesReferencia(YearMonth.now().plusMonths(1));
        repositorio = new PlanoInsumosRepositorioEmMemoria();
        servico = new PlanoInsumosServico(repositorio);
    }

    // Integração: criação e recuperação direta do repositório
    @Test
    void deveCriarPlanoERecuperarDoRepositorioPorId() {
        PlanoInsumos plano = servico.criarPlano(talhaoId, contaId, mesFuturo);

        Optional<PlanoInsumos> recuperado = repositorio.buscarPorId(plano.getId());

        assertThat(recuperado).isPresent();
        assertThat(recuperado.get().getTalhaoId()).isEqualTo(talhaoId);
        assertThat(recuperado.get().getMesReferencia()).isEqualTo(mesFuturo);
    }

    // Integração US-28 RN-103: duplicata detectada via repositório real
    @Test
    void deveRejeitarPlanoDuplicadoAoConsultarRepositorioReal() {
        servico.criarPlano(talhaoId, contaId, mesFuturo);

        assertThatThrownBy(() -> servico.criarPlano(talhaoId, contaId, mesFuturo))
                .isInstanceOf(InsumoDomainException.class)
                .hasMessage("Ja existe plano de insumos para esta Zona neste mes");
    }

    // Integração: zonas diferentes permitem planos no mesmo mês
    @Test
    void devePermitirPlanosParaZonasDiferentesNoMesmoMes() {
        TalhaoId outraTalhao = TalhaoId.novo();

        PlanoInsumos plano1 = servico.criarPlano(talhaoId, contaId, mesFuturo);
        PlanoInsumos plano2 = servico.criarPlano(outraTalhao, contaId, mesFuturo);

        assertThat(plano1.getId()).isNotEqualTo(plano2.getId());
        assertThat(repositorio.listarPorTalhao(talhaoId)).hasSize(1);
        assertThat(repositorio.listarPorTalhao(outraTalhao)).hasSize(1);
    }

    // Integração US-30: histórico retorna vazio quando plano ainda não está encerrado
    @Test
    void deveExcluirPlanosAtivosDoHistorico() {
        PlanoInsumos plano = servico.criarPlano(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        repositorio.salvar(plano);

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).isEmpty();
    }

    // Integração US-30 positivo: fluxo completo de criação, aquisição, encerramento e consulta
    @Test
    void deveConsultarHistoricoAposEncerrarPlanoComItensAdquiridos() {
        PlanoInsumos plano = servico.criarPlano(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.encerrar();
        repositorio.salvar(plano);

        List<ItemInsumo> historico = servico.consultarHistorico(talhaoId);

        assertThat(historico).hasSize(1);
        assertThat(historico.get(0).getStatus()).isEqualTo(StatusItemInsumo.ADQUIRIDO);
        assertThat(historico.get(0).getPrecoUnitario().getValor()).isEqualByComparingTo(new BigDecimal("10.00"));
    }

    // Integração US-46 + US-48: fluxo completo de consumo com pedido de reposição automático
    @Test
    void deveGerarPedidoReposicaoAoAtingirLimiteMinimoNoFluxoCompleto() {
        PlanoInsumos plano = servico.criarPlano(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");

        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(16), UnidadeMedidaInsumo.KG);

        assertThat(plano.getPedidosReposicao()).hasSize(1);
        PedidoReposicao pedido = plano.getPedidosReposicao().get(0);
        assertThat(pedido.getTipo()).isEqualTo(TipoInsumo.FERTILIZANTE);
        assertThat(pedido.getTalhaoId()).isEqualTo(talhaoId);
        assertThat(pedido.getSaldoNoMomento()).isEqualTo(4.0);
        assertThat(pedido.getEmailDestinatario()).isEqualTo("gestor@fazenda.com");
        assertThat(pedido.getStatusEnvio()).isEqualTo(StatusEnvioPedido.PENDENTE);
    }

    // Integração US-29: fluxo completo de aquisição e verificação do estado final
    @Test
    void deveManterStatusAdquiridoAposRegistrarAquisicao() {
        PlanoInsumos plano = servico.criarPlano(talhaoId, contaId, mesFuturo);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);

        plano.registrarAquisicao(item.getId(), new PrecoUnitario(new BigDecimal("25.00")));
        repositorio.salvar(plano);

        Optional<PlanoInsumos> recuperado = repositorio.buscarPorId(plano.getId());
        assertThat(recuperado).isPresent();
        assertThat(recuperado.get().getItens().get(0).getStatus()).isEqualTo(StatusItemInsumo.ADQUIRIDO);
        assertThat(recuperado.get().getItens().get(0).getPrecoUnitario().getValor())
                .isEqualByComparingTo(new BigDecimal("25.00"));
    }
}
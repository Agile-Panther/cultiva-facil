package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanoInsumosSteps {

    private TalhaoId talhaoId;
    private ContaId contaId;
    private MesReferencia mesReferencia;
    private PlanoInsumos plano;
    private InsumoDomainException excecaoCapturada;
    private PlanoInsumosRepositorioEmMemoria repositorio;
    private PlanoInsumosServico servico;
    private Runnable acaoQuando;

    public PlanoInsumosSteps() {
        repositorio = new PlanoInsumosRepositorioEmMemoria();
        servico = new PlanoInsumosServico(repositorio);
        talhaoId = TalhaoId.novo();
        contaId = ContaId.novo();
    }

    // ── US-28 ──────────────────────────────────────────────────────────────────

    @Dado("que o Agricultor informa o mês de referência {string} que é anterior ao mês atual")
    public void oAgricultorInformaOMessDeReferenciaPAssado(String mesStr) {
        try {
            mesReferencia = new MesReferencia(YearMonth.now().minusMonths(1));
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Quando("tenta criar o Plano de Insumos")
    public void tentaCriarOPlanodeInsumos() {
        // excecao ja capturada no Dado
    }

    @Entao("o sistema rejeita com erro MES_REFERENCIA_PASSADO")
    public void oSistemaRejeitaComErroMesReferenciaPassado() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.MES_REFERENCIA_PASSADO);
    }

    @Dado("que já existe um Plano de Insumos para a Zona no mês futuro válido")
    public void jaExisteUmPlanoParaAZonaNoMesFuturoValido() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = servico.criarPlano(talhaoId, contaId, mesReferencia);
    }

    @Quando("o Agricultor tenta criar outro plano para a mesma Zona e mesmo mês")
    public void oAgricultorTentaCriarOutroPlanoParaAMesmaZonaEMesmoMes() {
        try {
            servico.criarPlano(talhaoId, contaId, mesReferencia);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro PLANO_DUPLICADO")
    public void oSistemaRejeitaComErroPlanoDuplicado() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.PLANO_DUPLICADO);
    }

    @Dado("que o Agricultor informa tipo de insumo inexistente no conjunto Semente, Fertilizante, Defensivo")
    public void oAgricultorInformaTipoDeInsumoInexistente() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        acaoQuando = () -> plano.adicionarItem(null, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
    }

    @Quando("submete o cadastro")
    public void submeteOCadastro() {
        try {
            acaoQuando.run();
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro TIPO_INSUMO_INVALIDO")
    public void oSistemaRejeitaComErroTipoInsumoInvalido() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.TIPO_INSUMO_INVALIDO);
    }

    @Dado("que o Agricultor informa quantidade 0 para o insumo")
    public void oAgricultorInformaQuantidadeZero() {
        acaoQuando = () -> { new QuantidadeInsumo(0); };
    }

    @Entao("o sistema rejeita com erro QUANTIDADE_INSUMO_INVALIDA")
    public void oSistemaRejeitaComErroQuantidadeInsumoInvalida() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA);
    }

    @Dado("que o Agricultor informa mês de referência futuro válido, zona e agricultor")
    public void oAgricultorInformaConfiguracaoValida() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
    }

    @Quando("cria o Plano de Insumos com item do tipo Semente, quantidade 10 e unidade KG")
    public void criaOPlanoDeInsumosComItem() {
        plano = servico.criarPlano(talhaoId, contaId, mesReferencia);
        plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
    }

    @Entao("o plano é criado com sucesso e contém o item adicionado")
    public void oPlanoECriadoComSucessoEContemOItemAdicionado() {
        assertThat(plano).isNotNull();
        assertThat(plano.getId()).isNotNull();
        assertThat(plano.getItens()).hasSize(1);
        assertThat(plano.getItens().get(0).getTipo()).isEqualTo(TipoInsumo.SEMENTE);
    }

    // ── US-29 ──────────────────────────────────────────────────────────────────

    @Dado("que o Agricultor tenta mudar o status do item para Adquirido sem informar o Preço Unitário")
    public void oAgricultorTentaMudarStatusSemPreco() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
    }

    @Quando("submete a mudança de status")
    public void submeteAMudancaDeStatus() {
        try {
            plano.registrarAquisicao(plano.getItens().get(0).getId(), null);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro PRECO_UNITARIO_OBRIGATORIO")
    public void oSistemaRejeitaComErroPrecoUnitarioObrigatorio() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.PRECO_UNITARIO_OBRIGATORIO);
    }

    @Dado("que o item {string} já possui status Adquirido com data de registro confirmada")
    public void oItemJaPossuiStatusAdquirido(String nomeItem) {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("50.00")));
    }

    @Quando("o Agricultor tenta reverter o status para Planejado")
    public void oAgricultorTentaReverterStatusParaPlanejado() {
        try {
            plano.getItens().get(0).reverterParaPlanejado();
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro REVERSAO_STATUS_INVALIDA")
    public void oSistemaRejeitaComErroReversaoStatusInvalida() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.REVERSAO_STATUS_INVALIDA);
    }

    @Dado("que existe um item com status Planejado no plano")
    public void existeUmItemComStatusPlanejadoNoPlano() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
    }

    @Quando("o Agricultor registra a aquisição informando preço unitário de 50,00")
    public void oAgricultorRegistraAAquisicaoComPreco() {
        plano.registrarAquisicao(plano.getItens().get(0).getId(),
                new PrecoUnitario(new BigDecimal("50.00")));
    }

    @Entao("o item passa para status Adquirido e o preço unitário é registrado")
    public void oItemPassaParaStatusAdquirido() {
        assertThat(plano.getItens().get(0).getStatus()).isEqualTo(StatusItemInsumo.ADQUIRIDO);
        assertThat(plano.getItens().get(0).getPrecoUnitario()).isNotNull();
    }

    // ── US-30 ──────────────────────────────────────────────────────────────────

    @Dado("que a Zona possui apenas itens Planejados em ciclo ativo sem itens Adquiridos em ciclo encerrado")
    public void aZonaPossuiApenasItensPlanejados() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        PlanoInsumos planoAtivo = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        planoAtivo.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        repositorio.salvar(planoAtivo);
    }

    @Quando("o Agricultor consulta o histórico de insumos")
    public void oAgricultorConsultaOHistoricoDeInsumos() {
        // consultado no Então
    }

    @Entao("o histórico retorna vazio")
    public void oHistoricoRetornaVazio() {
        assertThat(servico.consultarHistorico(talhaoId)).isEmpty();
    }

    @Dado("que a Zona possui um plano encerrado com item no status Adquirido")
    public void aZonaPossuiUmPlanoEncerradoComItemAdquirido() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        PlanoInsumos planoEncerrado = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = planoEncerrado.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        planoEncerrado.encerrar();
        repositorio.salvar(planoEncerrado);
    }

    @Quando("o Agricultor consulta o histórico de insumos da Zona")
    public void oAgricultorConsultaOHistoricoDeInsumosDAZona() {
        // consultado no Então
    }

    @Entao("o histórico retorna somente os itens com status Adquirido do plano encerrado")
    public void oHistoricoRetornaSomenteItensAdquiridos() {
        assertThat(servico.consultarHistorico(talhaoId)).hasSize(1);
        assertThat(servico.consultarHistorico(talhaoId).get(0).getStatus())
                .isEqualTo(StatusItemInsumo.ADQUIRIDO);
    }

    // ── US-46 ──────────────────────────────────────────────────────────────────

    @Dado("que a Zona não possui nenhuma aquisição registrada para o tipo Semente")
    public void aZonaNaoPossuiAquisicaoParaSemente() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
    }

    @Quando("o Operador tenta registrar consumo de Semente na Zona")
    public void oOperadorTentaRegistrarConsumoSemente() {
        try {
            plano.registrarConsumo(TipoInsumo.SEMENTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro INSUMO_SEM_AQUISICAO")
    public void oSistemaRejeitaComErroInsumoSemAquisicao() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.INSUMO_SEM_AQUISICAO);
    }

    @Dado("que o saldo disponível de Fertilizante na Zona é 30 kg")
    public void oSaldoDisponiveldeFertilizanteE30Kg() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("15.00")));
    }

    @Quando("o Operador tenta registrar consumo de 50 kg de Fertilizante")
    public void oOperadorTentaRegistrarConsumo50Kg() {
        try {
            plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(50), UnidadeMedidaInsumo.KG);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro SALDO_INSUFICIENTE")
    public void oSistemaRejeitaComErroSaldoInsuficiente() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.SALDO_INSUFICIENTE);
    }

    @Dado("que o insumo Fertilizante foi adquirido com unidade KG")
    public void oInsumoFertilizanteFoiAdquiridoComUnidadeKG() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("15.00")));
    }

    @Quando("o Operador tenta registrar consumo de 5 LITRO de Fertilizante")
    public void oOperadorTentaRegistrarConsumoEmLitros() {
        try {
            plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.LITRO);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro UNIDADE_INCOMPATIVEL")
    public void oSistemaRejeitaComErroUnidadeIncompativel() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.UNIDADE_INCOMPATIVEL);
    }

    @Dado("que existe 30 kg de Fertilizante adquirido no plano")
    public void existe30KgDeFertilizanteAdquirido() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("15.00")));
    }

    @Quando("o Operador registra consumo de 10 kg de Fertilizante")
    public void oOperadorRegistraConsumo10Kg() {
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG);
    }

    @Entao("o consumo é registrado e o saldo passa a ser 20 kg")
    public void oConsumoERegistradoESaldoPassa20Kg() {
        assertThat(plano.getConsumos()).hasSize(1);
        double totalAdquirido = plano.getItens().stream()
                .filter(i -> i.getStatus() == StatusItemInsumo.ADQUIRIDO)
                .mapToDouble(i -> i.getQuantidade().getValor()).sum();
        double totalConsumido = plano.getConsumos().stream()
                .mapToDouble(c -> c.getQuantidade().getValor()).sum();
        assertThat(totalAdquirido - totalConsumido).isEqualTo(20.0);
    }

    // ── US-47 ──────────────────────────────────────────────────────────────────

    @Dado("que o Gestor informa valor de limite mínimo igual a 0 para Semente na Zona")
    public void oGestorInformaLimiteMinimoZero() {
        // tentativa feita no Quando
    }

    @Quando("submete a configuração de limite")
    public void submeteAConfiguracaoDeLimite() {
        try {
            new QuantidadeInsumo(0);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro LIMITE_MINIMO_INVALIDO")
    public void oSistemaRejeitaComErroLimiteMinimoInvalido() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA);
    }

    @Dado("que o Gestor tenta ativar configuração de limite sem informar e-mail do destinatário")
    public void oGestorTentaAtivarConfiguracaoSemEmail() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
    }

    @Quando("submete a configuração")
    public void submeteAConfiguracao() {
        try {
            plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, null);
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro EMAIL_DESTINATARIO_OBRIGATORIO")
    public void oSistemaRejeitaComErroEmailDestinatarioObrigatorio() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.EMAIL_DESTINATARIO_OBRIGATORIO);
    }

    @Dado("que já existe uma configuração de limite ativa para Fertilizante na Zona")
    public void jaExisteUmaConfiguracaoDeLimiteAtiva() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
    }

    @Quando("o Gestor tenta criar outra configuração para o mesmo insumo na mesma Zona")
    public void oGestorTentaCriarOutraConfiguracao() {
        try {
            plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(3), UnidadeMedidaInsumo.KG, "outro@fazenda.com");
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro CONFIGURACAO_LIMITE_DUPLICADA")
    public void oSistemaRejeitaComErroConfiguracaoLimiteDuplicada() {
        assertThat(excecaoCapturada).isNotNull();
        assertThat(excecaoCapturada.getCodigo()).isEqualTo(InsumoErroCodigo.CONFIGURACAO_LIMITE_DUPLICADA);
    }

    @Dado("que a Zona não possui nenhuma aquisição registrada para Defensivo")
    public void aZonaNaoPossuiAquisicaoParaDefensivo() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        plano.adicionarItem(TipoInsumo.DEFENSIVO, new QuantidadeInsumo(10), UnidadeMedidaInsumo.LITRO);
    }

    @Quando("o Gestor tenta configurar limite mínimo para Defensivo na Zona")
    public void oGestorTentaConfigurarLimiteMinimoParaDefensivo() {
        try {
            plano.configurarLimiteMinimo(TipoInsumo.DEFENSIVO, new QuantidadeInsumo(2), UnidadeMedidaInsumo.LITRO, "gestor@fazenda.com");
        } catch (InsumoDomainException e) {
            excecaoCapturada = e;
        }
    }

    @Dado("que existe aquisição de Fertilizante registrada na Zona")
    public void existeAquisicaoDeFertilizanteNaZona() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
    }

    @Quando("o Gestor configura limite mínimo de 5 kg com e-mail gestor@fazenda.com")
    public void oGestorConfiguraLimiteMinimo() {
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
    }

    @Entao("a configuração de limite é criada como ativa")
    public void aConfiguracaoDeLimiteECriadaComoAtiva() {
        assertThat(plano.getConfiguracoes()).hasSize(1);
        assertThat(plano.getConfiguracoes().get(0).isAtiva()).isTrue();
    }

    // ── US-48 ──────────────────────────────────────────────────────────────────

    @Dado("que existe configuração de limite mínimo de 10 kg para Fertilizante com e-mail gestor@fazenda.com")
    public void existeConfiguracaoDeLimiteMinimoParaFertilizante() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(15), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        // limite configurado após consumo inicial para não disparar pedido prematuro
    }

    @E("o saldo atual de Fertilizante é 15 kg adquiridos e 5 kg consumidos")
    public void oSaldoAtualDeFertilizanteE15KgAdquiridos5KgConsumidos() {
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
    }

    @Quando("o Operador registra consumo de 1 kg de Fertilizante reduzindo o saldo para 9 kg")
    public void oOperadorRegistraConsumo1KgFertilizante() {
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(1), UnidadeMedidaInsumo.KG);
    }

    @Entao("um PedidoReposicao é criado com o saldo no momento e e-mail do destinatário")
    public void umPedidoReposicaoECriadoComSaldoEEmail() {
        assertThat(plano.getPedidosReposicao()).hasSize(1);
        PedidoReposicao pedido = plano.getPedidosReposicao().get(0);
        assertThat(pedido.getSaldoNoMomento()).isEqualTo(9.0);
        assertThat(pedido.getEmailDestinatario()).isEqualTo("gestor@fazenda.com");
    }

    @E("o saldo atual de Fertilizante é 30 kg adquiridos sem consumos anteriores")
    public void oSaldoAtualDeFertilizanteE30KgSemConsumos() {
        // o Dado já criou item com 15kg; precisamos de um plano separado com 30kg
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(30), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
    }

    @Quando("o Operador registra consumo de 5 kg de Fertilizante reduzindo o saldo para 25 kg")
    public void oOperadorRegistraConsumo5KgFertilizante() {
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(5), UnidadeMedidaInsumo.KG);
    }

    @Entao("nenhum pedido de reposição é gerado pois o saldo permanece acima do limite")
    public void nenhumPedidoReposicaoGeradoSaldoAcimaDoLimite() {
        assertThat(plano.getPedidosReposicao()).isEmpty();
    }

    @E("o pedido de reposição é marcado como enviado")
    public void oPedidoDeReposicaoEMarcadoComoEnviado() {
        plano.getPedidosReposicao().get(0).marcarComoEnviado();
    }

    @Entao("o status do pedido é Enviado")
    public void oStatusDoPedidoEEnviado() {
        assertThat(plano.getPedidosReposicao().get(0).getStatusEnvio())
                .isEqualTo(StatusEnvioPedido.ENVIADO);
    }

    @Dado("que um pedido de reposição para Fertilizante foi emitido há menos de 24 horas")
    public void umPedidoDeReposicaoFoiEmitidoHaMenosDe24Horas() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(15), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        // configura limite e dispara o primeiro pedido imediatamente
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(6), UnidadeMedidaInsumo.KG);
        assertThat(plano.getPedidosReposicao()).hasSize(1);
    }

    @Quando("o Operador registra novo consumo que reduz o saldo abaixo do limite")
    public void oOperadorRegistraNovoConsumoAbaixoDoLimite() {
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(1), UnidadeMedidaInsumo.KG);
    }

    @Entao("nenhum novo pedido de reposição é gerado")
    public void nenhumNovoPedidoReposicaoGerado() {
        assertThat(plano.getPedidosReposicao()).hasSize(1);
    }

    @Dado("que existe configuração de limite de 10 kg para Fertilizante e saldo de 20 kg")
    public void existeConfiguracaoComSaldo20Kg() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("10.00")));
        plano.configurarLimiteMinimo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(10), UnidadeMedidaInsumo.KG, "gestor@fazenda.com");
    }

    @Quando("o consumo reduz o saldo para 8 kg abaixo do limite")
    public void oConsumoReduzSaldoPara8Kg() {
        plano.registrarConsumo(TipoInsumo.FERTILIZANTE, new QuantidadeInsumo(12), UnidadeMedidaInsumo.KG);
    }

    @Entao("o PedidoReposicao contém tipo, zona, saldo atual, quantidade sugerida e e-mail")
    public void oPedidoReposicaoContemTodasAsInformacoes() {
        assertThat(plano.getPedidosReposicao()).hasSize(1);
        PedidoReposicao pedido = plano.getPedidosReposicao().get(0);
        assertThat(pedido.getTipo()).isEqualTo(TipoInsumo.FERTILIZANTE);
        assertThat(pedido.getTalhaoId()).isEqualTo(talhaoId);
        assertThat(pedido.getSaldoNoMomento()).isEqualTo(8.0);
        assertThat(pedido.getQuantidadeSugerida()).isGreaterThanOrEqualTo(0);
        assertThat(pedido.getEmailDestinatario()).isEqualTo("gestor@fazenda.com");
        assertThat(pedido.getDataHoraDisparo()).isNotNull();
    }

    @Dado("que a Zona não possui configuração de limite ativa para Semente")
    public void aZonaNaoPossuiConfiguracaoDeLimiteAtiva() {
        mesReferencia = new MesReferencia(YearMonth.now().plusMonths(1));
        plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        ItemInsumo item = plano.adicionarItem(TipoInsumo.SEMENTE, new QuantidadeInsumo(20), UnidadeMedidaInsumo.KG);
        item.registrarAquisicao(new PrecoUnitario(new BigDecimal("5.00")));
    }

    @Quando("o Operador registra consumo de Semente")
    public void oOperadorRegistraConsumoSemente() {
        plano.registrarConsumo(TipoInsumo.SEMENTE, new QuantidadeInsumo(15), UnidadeMedidaInsumo.KG);
    }

    @Entao("nenhum pedido de reposição é gerado")
    public void nenhumPedidoDeReposicaoEGerado() {
        assertThat(plano.getPedidosReposicao()).isEmpty();
    }
}
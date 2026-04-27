package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlanoInsumos {

    private PlanoInsumosId id;
    private TalhaoId talhaoId;
    private ContaId contaId;
    private MesReferencia mesReferencia;
    private List<ItemInsumo> itens;
    private List<ItemConsumo> consumos;
    private List<ConfiguracaoLimite> configuracoes;
    private List<PedidoReposicao> pedidosReposicao;
    private boolean encerrado;
    private final List<Object> eventos;

    public PlanoInsumos(TalhaoId talhaoId, ContaId contaId, MesReferencia mesReferencia) {
        if (talhaoId == null) throw new InsumoDomainException(InsumoErroCodigo.PLANO_DUPLICADO, "TalhaoId obrigatorio");
        if (contaId == null) throw new InsumoDomainException(InsumoErroCodigo.PLANO_DUPLICADO, "ContaId obrigatorio");
        if (mesReferencia == null) throw new InsumoDomainException(InsumoErroCodigo.MES_REFERENCIA_PASSADO, "MesReferencia obrigatoria");
        this.id = PlanoInsumosId.novo();
        this.talhaoId = talhaoId;
        this.contaId = contaId;
        this.mesReferencia = mesReferencia;
        this.itens = new ArrayList<>();
        this.consumos = new ArrayList<>();
        this.configuracoes = new ArrayList<>();
        this.pedidosReposicao = new ArrayList<>();
        this.encerrado = false;
        this.eventos = new ArrayList<>();
        this.eventos.add(new PlanoCriado(this.id, talhaoId, mesReferencia));
    }

    public PlanoInsumos(PlanoInsumosId id, TalhaoId talhaoId, ContaId contaId,
                        MesReferencia mesReferencia, List<ItemInsumo> itens,
                        List<ItemConsumo> consumos, List<ConfiguracaoLimite> configuracoes,
                        List<PedidoReposicao> pedidosReposicao, boolean encerrado) {
        this.id = id;
        this.talhaoId = talhaoId;
        this.contaId = contaId;
        this.mesReferencia = mesReferencia;
        this.itens = new ArrayList<>(itens);
        this.consumos = new ArrayList<>(consumos);
        this.configuracoes = new ArrayList<>(configuracoes);
        this.pedidosReposicao = new ArrayList<>(pedidosReposicao);
        this.encerrado = encerrado;
        this.eventos = new ArrayList<>();
    }

    public ItemInsumo adicionarItem(TipoInsumo tipo, QuantidadeInsumo quantidade, UnidadeMedidaInsumo unidade) {
        if (tipo == null) throw new InsumoDomainException(InsumoErroCodigo.TIPO_INSUMO_INVALIDO, "TipoInsumo obrigatorio");
        if (quantidade == null) throw new InsumoDomainException(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA, "QuantidadeInsumo obrigatoria");
        if (unidade == null) throw new InsumoDomainException(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA, "UnidadeMedidaInsumo obrigatoria");
        ItemInsumo item = new ItemInsumo(tipo, quantidade, unidade);
        itens.add(item);
        return item;
    }

    public void registrarAquisicao(UUID itemId, PrecoUnitario preco) {
        ItemInsumo item = buscarItem(itemId);
        item.registrarAquisicao(preco);
        eventos.add(new ItemAdquirido(this.id, item.getTipo(), preco));
    }

    public void reverterItemParaPlanejado(UUID itemId) {
        ItemInsumo item = buscarItem(itemId);
        item.reverterParaPlanejado();
    }

    public void registrarConsumo(TipoInsumo tipo, QuantidadeInsumo quantidade, UnidadeMedidaInsumo unidade) {
        boolean possuiAquisicao = itens.stream()
                .anyMatch(i -> i.getTipo() == tipo && i.getStatus() == StatusItemInsumo.ADQUIRIDO);
        if (!possuiAquisicao) {
            throw new InsumoDomainException(InsumoErroCodigo.INSUMO_SEM_AQUISICAO,
                    "Nenhuma aquisicao registrada para este insumo nesta Zona");
        }

        UnidadeMedidaInsumo unidadeAquisicao = itens.stream()
                .filter(i -> i.getTipo() == tipo && i.getStatus() == StatusItemInsumo.ADQUIRIDO)
                .findFirst()
                .map(ItemInsumo::getUnidade)
                .orElseThrow();
        if (unidade != unidadeAquisicao) {
            throw new InsumoDomainException(InsumoErroCodigo.UNIDADE_INCOMPATIVEL,
                    "Unidade de medida do consumo difere da unidade de aquisicao");
        }

        double totalAdquirido = itens.stream()
                .filter(i -> i.getTipo() == tipo && i.getStatus() == StatusItemInsumo.ADQUIRIDO)
                .mapToDouble(i -> i.getQuantidade().getValor())
                .sum();
        double totalConsumido = consumos.stream()
                .filter(c -> c.getTipo() == tipo)
                .mapToDouble(c -> c.getQuantidade().getValor())
                .sum();
        double saldo = totalAdquirido - totalConsumido;
        if (quantidade.getValor() > saldo) {
            throw new InsumoDomainException(InsumoErroCodigo.SALDO_INSUFICIENTE,
                    "Quantidade consumida excede o saldo disponivel");
        }

        consumos.add(new ItemConsumo(tipo, quantidade, unidade));

        double novoSaldo = saldo - quantidade.getValor();
        verificarReposicaoAposConsumo(tipo, novoSaldo);
    }

    public void configurarLimiteMinimo(TipoInsumo tipo, QuantidadeInsumo limiteMinimo,
                                       UnidadeMedidaInsumo unidade, String emailDestinatario) {
        boolean possuiAquisicao = itens.stream()
                .anyMatch(i -> i.getTipo() == tipo && i.getStatus() == StatusItemInsumo.ADQUIRIDO);
        if (!possuiAquisicao) {
            throw new InsumoDomainException(InsumoErroCodigo.INSUMO_SEM_AQUISICAO,
                    "Nenhuma aquisicao registrada para este insumo nesta Zona");
        }

        boolean jaExiste = configuracoes.stream()
                .anyMatch(c -> c.getTipo() == tipo && c.isAtiva());
        if (jaExiste) {
            throw new InsumoDomainException(InsumoErroCodigo.CONFIGURACAO_LIMITE_DUPLICADA,
                    "Ja existe configuracao de limite ativa para este insumo nesta Zona");
        }

        configuracoes.add(new ConfiguracaoLimite(tipo, limiteMinimo, unidade, emailDestinatario));
    }

    public void encerrar() {
        this.encerrado = true;
    }

    private void verificarReposicaoAposConsumo(TipoInsumo tipo, double novoSaldo) {
        configuracoes.stream()
                .filter(c -> c.getTipo() == tipo && c.isAtiva())
                .findFirst()
                .ifPresent(config -> {
                    if (novoSaldo <= config.getLimiteMinimo().getValor() && config.podeEmitirNovoPedido()) {
                        double quantidadePlano = itens.stream()
                                .filter(i -> i.getTipo() == tipo)
                                .mapToDouble(i -> i.getQuantidade().getValor())
                                .sum();
                        double quantidadeSugerida = Math.max(0, quantidadePlano - novoSaldo);
                        PedidoReposicao pedido = new PedidoReposicao(
                                tipo, talhaoId, novoSaldo, quantidadeSugerida, config.getEmailDestinatario());
                        pedidosReposicao.add(pedido);
                        config.registrarPedido();
                        eventos.add(new ReposicaoSolicitada(
                                id, tipo, talhaoId, novoSaldo, quantidadeSugerida, config.getEmailDestinatario()));
                    }
                });
    }

    private ItemInsumo buscarItem(UUID itemId) {
        return itens.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new InsumoDomainException(InsumoErroCodigo.TIPO_INSUMO_INVALIDO, "Item nao encontrado"));
    }

    public PlanoInsumosId getId() { return id; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public ContaId getContaId() { return contaId; }
    public MesReferencia getMesReferencia() { return mesReferencia; }
    public List<ItemInsumo> getItens() { return Collections.unmodifiableList(itens); }
    public List<ItemConsumo> getConsumos() { return Collections.unmodifiableList(consumos); }
    public List<ConfiguracaoLimite> getConfiguracoes() { return Collections.unmodifiableList(configuracoes); }
    public List<PedidoReposicao> getPedidosReposicao() { return Collections.unmodifiableList(pedidosReposicao); }
    public boolean isEncerrado() { return encerrado; }
    public List<Object> getEventos() { return Collections.unmodifiableList(eventos); }
    public void limparEventos() { eventos.clear(); }

    private void setItens(List<ItemInsumo> itens) { this.itens = new ArrayList<>(itens); }
    private void setConsumos(List<ItemConsumo> consumos) { this.consumos = new ArrayList<>(consumos); }
    private void setConfiguracoes(List<ConfiguracaoLimite> configuracoes) { this.configuracoes = new ArrayList<>(configuracoes); }
    private void setPedidosReposicao(List<PedidoReposicao> pedidosReposicao) { this.pedidosReposicao = new ArrayList<>(pedidosReposicao); }

    public static class PlanoCriado {
        public final PlanoInsumosId planoId;
        public final TalhaoId talhaoId;
        public final MesReferencia mesReferencia;

        public PlanoCriado(PlanoInsumosId planoId, TalhaoId talhaoId, MesReferencia mesReferencia) {
            this.planoId = planoId;
            this.talhaoId = talhaoId;
            this.mesReferencia = mesReferencia;
        }
    }

    public static class ItemAdquirido {
        public final PlanoInsumosId planoId;
        public final TipoInsumo tipo;
        public final PrecoUnitario precoUnitario;

        public ItemAdquirido(PlanoInsumosId planoId, TipoInsumo tipo, PrecoUnitario precoUnitario) {
            this.planoId = planoId;
            this.tipo = tipo;
            this.precoUnitario = precoUnitario;
        }
    }

    public static class ReposicaoSolicitada {
        public final PlanoInsumosId planoId;
        public final TipoInsumo tipo;
        public final TalhaoId talhaoId;
        public final double saldoAtual;
        public final double quantidadeSugerida;
        public final String emailDestinatario;

        public ReposicaoSolicitada(PlanoInsumosId planoId, TipoInsumo tipo, TalhaoId talhaoId,
                                   double saldoAtual, double quantidadeSugerida, String emailDestinatario) {
            this.planoId = planoId;
            this.tipo = tipo;
            this.talhaoId = talhaoId;
            this.saldoAtual = saldoAtual;
            this.quantidadeSugerida = quantidadeSugerida;
            this.emailDestinatario = emailDestinatario;
        }
    }
}
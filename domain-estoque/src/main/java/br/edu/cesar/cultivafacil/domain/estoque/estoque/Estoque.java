package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Estoque {

    private final EstoqueId id;
    private final PropriedadeId propriedadeId;
    private LocalDate dataAtual;
    private final Map<ItemEstoqueId, ItemEstoque> itens;
    private final List<EntradaEstoque> entradas;
    private final List<SaidaEstoque> saidas;
    private final List<LimiteMinimoEstoque> limitesMinimos;
    private final List<Object> eventos;

    public Estoque(PropriedadeId propriedadeId, LocalDate dataAtual) {
        this(EstoqueId.novo(), propriedadeId, dataAtual, new LinkedHashMap<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Estoque(EstoqueId id, PropriedadeId propriedadeId, LocalDate dataAtual,
                   Map<ItemEstoqueId, ItemEstoque> itens,
                   List<EntradaEstoque> entradas,
                   List<SaidaEstoque> saidas,
                   List<LimiteMinimoEstoque> limitesMinimos) {
        this.id = Objects.requireNonNull(id, "EstoqueId nao pode ser nulo");
        this.propriedadeId = Objects.requireNonNull(propriedadeId, "PropriedadeId nao pode ser nulo");
        this.dataAtual = Objects.requireNonNull(dataAtual, "Data atual nao pode ser nula");
        this.itens = new LinkedHashMap<>(itens);
        this.entradas = new ArrayList<>(entradas);
        this.saidas = new ArrayList<>(saidas);
        this.limitesMinimos = new ArrayList<>(limitesMinimos);
        this.eventos = new ArrayList<>();
    }

    public ItemEstoqueId cadastrarItem(String nome, TipoItemEstoque tipo, UnidadeMedidaEstoque unidade) {
        var item = new ItemEstoque(nome, tipo, unidade);
        itens.put(item.id(), item);
        return item.id();
    }

    public void registrarEntrada(ItemEstoqueId itemId, String quantidade, String unidade,
                                 String origem, String referencia) {
        ItemEstoque item = itemObrigatorio(itemId);
        QuantidadeEstoque quantidadeEstoque = quantidadeValida(quantidade);
        UnidadeMedidaEstoque unidadeEstoque = unidadeValida(unidade);
        OrigemEntrada origemEntrada = OrigemEntrada.de(origem);
        item.exigirUnidade(unidadeEstoque);
        if (entradaDuplicada(itemId, quantidadeEstoque, origemEntrada)) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA: entrada duplicada");
        }

        item.adicionar(quantidadeEstoque);
        var entrada = new EntradaEstoque(itemId, quantidadeEstoque, unidadeEstoque,
                origemEntrada, normalizarReferencia(referencia), LocalDateTime.now());
        entradas.add(entrada);
        eventos.add(new EntradaEstoqueRegistrada(id, propriedadeId, itemId, quantidadeEstoque,
                origemEntrada, entrada.registradaEm()));
    }

    public void registrarSaida(ItemEstoqueId itemId, String quantidade, String motivo,
                               LocalDate dataSaida, String justificativaDescarte) {
        ItemEstoque item = itemObrigatorio(itemId);
        QuantidadeEstoque quantidadeEstoque = quantidadeValida(quantidade);
        MotivoSaida motivoSaida = MotivoSaida.de(motivo);
        validarJustificativaDescarte(motivoSaida, justificativaDescarte);
        validarDataSaida(dataSaida);

        item.retirar(quantidadeEstoque);
        saidas.add(new SaidaEstoque(itemId, quantidadeEstoque, motivoSaida,
                dataSaida, normalizarJustificativa(justificativaDescarte), LocalDateTime.now()));
        eventos.add(new SaidaEstoqueRegistrada(id, propriedadeId, itemId, quantidadeEstoque,
                motivoSaida, dataSaida));
        publicarAlertaSeAbaixoDoMinimo(item);
    }

    public void configurarLimiteMinimo(ItemEstoqueId itemId, String valor, String unidade) {
        ItemEstoque item = itemObrigatorio(itemId);
        QuantidadeEstoque limite = quantidadeValidaComErroEstoque(valor);
        UnidadeMedidaEstoque unidadeEstoque = unidadeValida(unidade);
        item.exigirUnidade(unidadeEstoque);
        if (limiteAtivoExistente(itemId)) {
            throw new IllegalArgumentException("ESTOQUE_INVALIDO: ja existe configuracao ativa para o item");
        }

        limitesMinimos.add(new LimiteMinimoEstoque(itemId, limite, unidadeEstoque, true, LocalDateTime.now()));
        eventos.add(new LimiteMinimoConfigurado(id, propriedadeId, itemId, limite));
        publicarAlertaSeAbaixoDoMinimo(item);
    }

    public Optional<ItemEstoqueId> buscarItemPorNome(String nome) {
        return itens.values().stream()
                .filter(item -> item.nome().equals(nome))
                .map(ItemEstoque::id)
                .findFirst();
    }

    public QuantidadeEstoque saldoDe(ItemEstoqueId itemId) {
        return itemObrigatorio(itemId).saldo();
    }

    public void alterarDataAtual(LocalDate dataAtual) {
        this.dataAtual = Objects.requireNonNull(dataAtual, "Data atual nao pode ser nula");
    }

    private boolean entradaDuplicada(ItemEstoqueId itemId, QuantidadeEstoque quantidade, OrigemEntrada origem) {
        return entradas.stream().anyMatch(entrada ->
                entrada.itemId().equals(itemId)
                        && entrada.quantidade().equals(quantidade)
                        && entrada.origem() == origem);
    }

    private void validarDataSaida(LocalDate dataSaida) {
        Objects.requireNonNull(dataSaida, "Data de saida nao pode ser nula");
        if (dataSaida.isAfter(dataAtual)) {
            throw new IllegalArgumentException("DATA_INVALIDA");
        }
    }

    private void validarJustificativaDescarte(MotivoSaida motivo, String justificativa) {
        if (motivo != MotivoSaida.DESCARTE) {
            return;
        }
        String texto = justificativa == null ? "" : justificativa.trim();
        if (texto.length() < 20 || texto.length() > 500) {
            throw new IllegalArgumentException("JUSTIFICATIVA_INVALIDA");
        }
    }

    private void publicarAlertaSeAbaixoDoMinimo(ItemEstoque item) {
        limitesMinimos.stream()
                .filter(limite -> limite.ativo() && limite.itemId().equals(item.id()))
                .filter(limite -> item.saldo().menorOuIgualA(limite.valor()))
                .findFirst()
                .ifPresent(limite -> eventos.add(new EstoqueAbaixoDoMinimo(
                        id, propriedadeId, item.id(), item.saldo(), limite.valor())));
    }

    private boolean limiteAtivoExistente(ItemEstoqueId itemId) {
        return limitesMinimos.stream().anyMatch(limite -> limite.ativo() && limite.itemId().equals(itemId));
    }

    private ItemEstoque itemObrigatorio(ItemEstoqueId itemId) {
        Objects.requireNonNull(itemId, "ItemEstoqueId nao pode ser nulo");
        ItemEstoque item = itens.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("ESTOQUE_INVALIDO");
        }
        return item;
    }

    private QuantidadeEstoque quantidadeValida(String valor) {
        try {
            return new QuantidadeEstoque(valor);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA", ex);
        }
    }

    private QuantidadeEstoque quantidadeValidaComErroEstoque(String valor) {
        try {
            return new QuantidadeEstoque(valor);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("ESTOQUE_INVALIDO", ex);
        }
    }

    private UnidadeMedidaEstoque unidadeValida(String unidade) {
        try {
            return new UnidadeMedidaEstoque(unidade);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("UNIDADE_INVALIDA", ex);
        }
    }

    private String normalizarReferencia(String referencia) {
        return referencia == null ? "" : referencia.trim();
    }

    private String normalizarJustificativa(String justificativa) {
        return justificativa == null ? null : justificativa.trim();
    }

    public EstoqueId id() {
        return id;
    }

    public PropriedadeId propriedadeId() {
        return propriedadeId;
    }

    public Collection<ItemEstoque> itens() {
        return Collections.unmodifiableCollection(itens.values());
    }

    public List<EntradaEstoque> entradas() {
        return Collections.unmodifiableList(entradas);
    }

    public List<SaidaEstoque> saidas() {
        return Collections.unmodifiableList(saidas);
    }

    public List<LimiteMinimoEstoque> limitesMinimos() {
        return Collections.unmodifiableList(limitesMinimos);
    }

    public List<Object> eventos() {
        return Collections.unmodifiableList(eventos);
    }
}

package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.util.Objects;

public class ItemEstoque {

    private final ItemEstoqueId id;
    private final String nome;
    private final TipoItemEstoque tipo;
    private final UnidadeMedidaEstoque unidade;
    private QuantidadeEstoque saldo;

    public ItemEstoque(String nome, TipoItemEstoque tipo, UnidadeMedidaEstoque unidade) {
        this(ItemEstoqueId.novo(), nome, tipo, unidade, QuantidadeEstoque.ZERO);
    }

    public ItemEstoque(ItemEstoqueId id, String nome, TipoItemEstoque tipo,
                       UnidadeMedidaEstoque unidade, QuantidadeEstoque saldo) {
        this.id = Objects.requireNonNull(id, "ItemEstoqueId nao pode ser nulo");
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("ESTOQUE_INVALIDO");
        }
        this.nome = nome.trim();
        this.tipo = Objects.requireNonNull(tipo, "TipoItemEstoque nao pode ser nulo");
        this.unidade = Objects.requireNonNull(unidade, "Unidade nao pode ser nula");
        this.saldo = Objects.requireNonNull(saldo, "Saldo nao pode ser nulo");
    }

    void adicionar(QuantidadeEstoque quantidade) {
        saldo = saldo.somar(quantidade);
    }

    void retirar(QuantidadeEstoque quantidade) {
        if (saldo.menorQue(quantidade)) {
            throw new IllegalArgumentException("QUANTIDADE_INVALIDA");
        }
        saldo = saldo.subtrair(quantidade);
    }

    void exigirUnidade(UnidadeMedidaEstoque unidadeInformada) {
        if (!unidade.equals(unidadeInformada)) {
            throw new IllegalArgumentException("UNIDADE_INVALIDA");
        }
    }

    public ItemEstoqueId id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public TipoItemEstoque tipo() {
        return tipo;
    }

    public UnidadeMedidaEstoque unidade() {
        return unidade;
    }

    public QuantidadeEstoque saldo() {
        return saldo;
    }
}

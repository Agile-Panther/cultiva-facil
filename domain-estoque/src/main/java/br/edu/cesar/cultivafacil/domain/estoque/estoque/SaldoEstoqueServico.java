package br.edu.cesar.cultivafacil.domain.estoque.estoque;

public class SaldoEstoqueServico {

    public QuantidadeEstoque saldoDoItem(Estoque estoque, ItemEstoqueId itemId) {
        return estoque.saldoDe(itemId);
    }
}

package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

public record EstoqueAbaixoDoMinimo(
        EstoqueId estoqueId,
        PropriedadeId propriedadeId,
        ItemEstoqueId itemId,
        QuantidadeEstoque saldo,
        QuantidadeEstoque limite
) {
}

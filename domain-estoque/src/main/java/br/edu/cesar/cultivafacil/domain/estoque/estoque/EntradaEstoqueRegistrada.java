package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.time.LocalDateTime;

public record EntradaEstoqueRegistrada(
        EstoqueId estoqueId,
        PropriedadeId propriedadeId,
        ItemEstoqueId itemId,
        QuantidadeEstoque quantidade,
        OrigemEntrada origem,
        LocalDateTime registradaEm
) {
}

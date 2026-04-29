package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.time.LocalDateTime;

public record EntradaEstoque(
        ItemEstoqueId itemId,
        QuantidadeEstoque quantidade,
        UnidadeMedidaEstoque unidade,
        OrigemEntrada origem,
        String referencia,
        LocalDateTime registradaEm
) {
}

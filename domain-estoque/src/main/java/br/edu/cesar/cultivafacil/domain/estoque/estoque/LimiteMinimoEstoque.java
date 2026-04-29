package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.time.LocalDateTime;

public record LimiteMinimoEstoque(
        ItemEstoqueId itemId,
        QuantidadeEstoque valor,
        UnidadeMedidaEstoque unidade,
        boolean ativo,
        LocalDateTime configuradoEm
) {
}

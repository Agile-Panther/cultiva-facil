package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.time.LocalDate;

public record SaidaEstoqueRegistrada(
        EstoqueId estoqueId,
        PropriedadeId propriedadeId,
        ItemEstoqueId itemId,
        QuantidadeEstoque quantidade,
        MotivoSaida motivo,
        LocalDate dataSaida
) {
}

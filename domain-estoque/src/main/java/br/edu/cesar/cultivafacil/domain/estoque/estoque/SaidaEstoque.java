package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SaidaEstoque(
        ItemEstoqueId itemId,
        QuantidadeEstoque quantidade,
        MotivoSaida motivo,
        LocalDate dataSaida,
        String justificativaDescarte,
        LocalDateTime registradaEm
) {
}

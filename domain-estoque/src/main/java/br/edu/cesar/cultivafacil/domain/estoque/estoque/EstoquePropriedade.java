package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EstoquePropriedade extends Estoque {

    public EstoquePropriedade(PropriedadeId propriedadeId, LocalDate dataAtual) {
        super(propriedadeId, dataAtual);
    }

    public EstoquePropriedade(EstoqueId id, PropriedadeId propriedadeId, LocalDate dataAtual,
                              Map<ItemEstoqueId, ItemEstoque> itens,
                              List<EntradaEstoque> entradas,
                              List<SaidaEstoque> saidas,
                              List<LimiteMinimoEstoque> limitesMinimos) {
        super(id, propriedadeId, dataAtual, itens, entradas, saidas, limitesMinimos);
    }
}

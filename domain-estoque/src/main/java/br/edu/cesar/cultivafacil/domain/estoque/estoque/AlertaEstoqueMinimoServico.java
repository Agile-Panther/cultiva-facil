package br.edu.cesar.cultivafacil.domain.estoque.estoque;

public class AlertaEstoqueMinimoServico {

    public boolean estaAbaixoOuNoLimite(QuantidadeEstoque saldo, QuantidadeEstoque limite) {
        return saldo.menorOuIgualA(limite);
    }
}

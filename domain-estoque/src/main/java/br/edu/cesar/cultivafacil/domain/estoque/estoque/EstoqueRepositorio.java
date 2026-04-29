package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import java.util.Optional;

public interface EstoqueRepositorio {

    Optional<Estoque> buscarPorId(EstoqueId id);

    void salvar(Estoque estoque);
}

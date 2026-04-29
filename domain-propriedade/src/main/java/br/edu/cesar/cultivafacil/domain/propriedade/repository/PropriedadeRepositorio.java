package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;

import java.util.Optional;

public interface PropriedadeRepositorio {

    void salvar(Propriedade propriedade);

    Optional<Propriedade> buscarPorId(PropriedadeId id);

    Optional<Propriedade> buscarPorContaId(ContaId contaId);

    boolean existePorContaId(ContaId contaId);
}

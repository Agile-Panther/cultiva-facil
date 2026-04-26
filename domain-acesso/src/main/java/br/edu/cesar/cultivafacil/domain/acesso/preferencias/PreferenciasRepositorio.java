package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;

import java.util.Optional;

public interface PreferenciasRepositorio {
    void salvar(Preferencias preferencias);
    Optional<Preferencias> buscarPorContaId(ContaId contaId);
}

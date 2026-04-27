package br.edu.cesar.cultivafacil.domain.acesso.conta;

import java.util.Optional;

public interface ContaRepositorio {

    void salvar(Conta conta);

    Optional<Conta> buscarPorEmail(Email email);

    Optional<Conta> buscarPorId(ContaId id);
}

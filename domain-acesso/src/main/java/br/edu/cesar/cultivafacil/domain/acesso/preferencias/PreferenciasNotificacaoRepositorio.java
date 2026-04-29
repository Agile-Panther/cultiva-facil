package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;

import java.util.Optional;

public interface PreferenciasNotificacaoRepositorio {

    void salvar(PreferenciasNotificacao preferencias);

    Optional<PreferenciasNotificacao> buscarPorContaId(ContaId contaId);

    Optional<PreferenciasNotificacao> buscarPorId(PreferenciasId id);
}
